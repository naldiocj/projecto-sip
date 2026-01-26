# Sistema de Gestão Processual - PostgreSQL Performance Guide

## 🎯 Resumo da Arquitetura

Sistema judicial com 34 tabelas + views materializadas para dashboard, com suporte a milhões de registros através de particionamento.

**Características principais:**
- Particionamento de `processos` por ano
- Índices estratégicos em colunas de filtro
- Views materializadas para agregações
- Triggers automáticos para `updated_at`
- Extensões: pg_trgm (busca fulltext), btree_gin

---

## 📋 Índices Criados

### Índices Simples (B-Tree)
```sql
-- Buscas exatas
idx_usuarios_usuario_id          -- Buscar usuário por ID
idx_processos_numero             -- Buscar processo por número
idx_queixosos_bi                 -- Buscar queixoso por BI
idx_advogados_cedula             -- Buscar advogado por cédula
idx_cartas_numero                -- Buscar carta precatória
```

### Índices GIN (Fulltext/LIKE)
```sql
-- Buscas por nome (ILIKE - case insensitive)
idx_usuarios_nome                -- Buscar usuário por nome
idx_tipos_crimes_descricao       -- Buscar crime por descrição
idx_queixosos_nome               -- Buscar queixoso por nome
idx_arguidos_nome                -- Buscar arguido por nome
idx_testemunhas_nome             -- Buscar testemunha por nome
idx_advogados_nome               -- Buscar advogado por nome
idx_magistrados_nome             -- Buscar magistrado por nome
```

### Índices Compostos
```sql
-- Queries que filtram múltiplas colunas
idx_capas_numerado_magistrado    -- Filtrar por processo + magistrado
idx_autos_declaracoes_tipo_data  -- Filtrar por tipo + data
```

### Índices em Timestamps
```sql
-- Queries por intervalo de data
idx_processos_created_at         -- Processos recentes
idx_capas_data                   -- Capas por período
idx_avisos_data_emissao          -- Avisos recentes
```

---

## 🔧 Configuração PostgreSQL

### postgresql.conf - Valores Recomendados

```ini
# ========================================
# CACHE E MEMÓRIA
# ========================================
# 25% da RAM disponível (ajuste conforme seu servidor)
shared_buffers = 4GB

# 75% da RAM disponível
effective_cache_size = 12GB

# Para operações de sort/hash
work_mem = 256MB

# Para VACUUM e CREATE INDEX
maintenance_work_mem = 1GB

# ========================================
# PARALELISMO (importante para tabelas grandes)
# ========================================
max_parallel_workers_per_gather = 4
max_parallel_workers = 8
max_parallel_maintenance_workers = 4

# ========================================
# PERFORMANCE - DISCOS
# ========================================
# Para SSDs (não HDD) - reduz random_page_cost
random_page_cost = 1.1

# ========================================
# CHECKPOINT (escreve dados em disco)
# ========================================
checkpoint_timeout = 15min
checkpoint_completion_target = 0.9
max_wal_size = 4GB

# ========================================
# LOGGING (monitoramento)
# ========================================
# Log queries mais lentas que 1 segundo
log_min_duration_statement = 1000
log_line_prefix = '%t [%p]: [%l-1] user=%u,db=%d,app=%a,client=%h '

# ========================================
# AUTOVÁCUO
# ========================================
autovacuum = on
autovacuum_naptime = 10s
autovacuum_vacuum_scale_factor = 0.1
autovacuum_analyze_scale_factor = 0.05
```

### Aplicar Configurações
```bash
# Opção 1: Editar postgresql.conf
sudo nano /etc/postgresql/15/main/postgresql.conf

# Opção 2: Via SQL (requer superuser)
ALTER SYSTEM SET shared_buffers = '4GB';
ALTER SYSTEM SET effective_cache_size = '12GB';
SELECT pg_reload_conf();

# Reiniciar PostgreSQL (se necessário)
sudo systemctl restart postgresql
```

---

## 🚀 Queries Otimizadas - Casos de Uso

### 1. Dashboard - Processos Recentes
```sql
-- Usa view materializada + índice
SELECT * FROM mv_processos_resumo
WHERE tipo_processo = 'Penal'
ORDER BY ultima_atualizacao DESC
LIMIT 20;
```

### 2. Buscar Processo por Número
```sql
-- Usa índice idx_processos_numero
SELECT p.*, tp.descricao
FROM processos p
JOIN tipos_processos tp ON p.tipo_processo_id = tp.id
WHERE p.numero = 'PRO-2025-001';
```

### 3. Buscar Pessoa por BI/Cedula
```sql
-- Buscas exatas (muito rápido)
SELECT * FROM queixosos WHERE numero_bi = '123456789LA';
SELECT * FROM arguidos WHERE numero_bi = '123456789LA';
SELECT * FROM testemunhas WHERE numero_bi = '123456789LA';
```

### 4. Buscar por Nome (Case Insensitive)
```sql
-- Usa índice GIN + pg_trgm
SELECT * FROM queixosos 
WHERE nome ILIKE '%silva%'
LIMIT 50;
```

### 5. Notificações Urgentes
```sql
-- Usa view materializada
SELECT * FROM mv_notificacoes_pendentes
WHERE status_urgencia IN ('URGENTE', 'EXPIRADO')
ORDER BY data_comparencia ASC;
```

### 6. Estatísticas por Período
```sql
-- Usa índice em timestamps
SELECT 
    DATE_TRUNC('month', cap.data_ocorrencia) as mes,
    COUNT(*) as total_casos
FROM capas_processos cap
WHERE cap.data_ocorrencia >= '2025-01-01'
GROUP BY DATE_TRUNC('month', cap.data_ocorrencia)
ORDER BY mes DESC;
```

---

## 📊 Monitoramento

### Ver Queries Lentas
```sql
-- Ativar extensão
CREATE EXTENSION pg_stat_statements;

-- Ver top 20 queries lentas
SELECT 
    query,
    calls,
    total_time,
    mean_time,
    max_time
FROM pg_stat_statements
WHERE mean_time > 1000
ORDER BY total_time DESC
LIMIT 20;

-- Resetar estatísticas
SELECT pg_stat_statements_reset();
```

### Ver Saúde do Banco
```sql
-- Tamanho total
SELECT pg_size_pretty(pg_database_size(current_database()));

-- Tamanho por tabela
SELECT 
    tablename,
    pg_size_pretty(pg_total_relation_size('public.'||tablename)) as size
FROM pg_tables
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size('public.'||tablename) DESC;

-- Índices não utilizados
SELECT 
    indexname,
    idx_scan,
    pg_size_pretty(pg_relation_size(indexrelname::regclass)) as size
FROM pg_stat_user_indexes
WHERE idx_scan = 0
ORDER BY pg_relation_size(indexrelname::regclass) DESC;
```

### Conexões Ativas
```sql
SELECT 
    datname,
    usename,
    state,
    COUNT(*) as count
FROM pg_stat_activity
WHERE datname IS NOT NULL
GROUP BY datname, usename, state;
```

---

## 🔄 Manutenção Periódica

### Diária
```sql
-- Análise de dados (otimiza estimativas do planner)
ANALYZE;

-- Limpeza de espaço morto
VACUUM;
```

### Semanal
```sql
-- Limpeza agressiva + análise
VACUUM ANALYZE;
```

### Mensal
```sql
-- Reindex de índices fragmentados
REINDEX DATABASE seu_banco_dados;
```

### Atualizar Views Materializadas
```sql
-- Pode ser agendado via cron
REFRESH MATERIALIZED VIEW CONCURRENTLY mv_processos_resumo;
REFRESH MATERIALIZED VIEW CONCURRENTLY mv_atividades_usuarios;
REFRESH MATERIALIZED VIEW CONCURRENTLY mv_crimes_estatisticas;
REFRESH MATERIALIZED VIEW CONCURRENTLY mv_notificacoes_pendentes;
```

---

## 💾 Backup e Restore

### Backup Completo
```bash
# Backup com formato plain-text (editável)
pg_dump seu_banco_dados > backup_$(date +%Y%m%d).sql

# Backup com formato custom (mais comprimido)
pg_dump -Fc seu_banco_dados > backup_$(date +%Y%m%d).dump
```

### Restore
```bash
# Restaurar do SQL
psql seu_banco_dados < backup_20250126.sql

# Restaurar do custom dump
pg_restore -d seu_banco_dados backup_20250126.dump
```

---

## 🚨 Troubleshooting

### Query Lenta
1. Executar `EXPLAIN ANALYZE` na query
2. Verificar se usa índices apropriados
3. Aumentar `work_mem` se tiver hash/sort sem índice
4. Adicionar índice faltante se necessário

### Tabela Crescendo Muito
1. Verificar tamanho: `SELECT pg_size_pretty(pg_total_relation_size('tabela'));`
2. Particionar se > 10GB
3. Arquivar dados antigos

### Banco Lento
1. Executar `VACUUM ANALYZE;`
2. Verificar `autovacuum` está ativo
3. Aumentar `shared_buffers` e `effective_cache_size`
4. Revisar índices não utilizados

### Alto uso de CPU
1. Ver queries lentas com `pg_stat_statements`
2. Executar `EXPLAIN ANALYZE` nestas queries
3. Adicionar índices faltantes
4. Atualizar estatísticas: `ANALYZE;`

---

## 📈 Escalabilidade Futura

### Se Volume Crescer (Bilhões de Registros)

**Opção 1: Particionamento Avançado**
```sql
-- Particionar por range + hash para paralelismo
CREATE TABLE processos_2025 PARTITION OF processos
    FOR VALUES FROM (2025) TO (2026)
    PARTITION BY HASH (id);
```

**Opção 2: Replica de Leitura**
```bash
# Criar réplica em outro servidor
# Separar leituras (queries) em réplica
# Master recebe escritas
```

**Opção 3: Sharding (Avançado)**
- Distribuir dados por `usuario_id` ou `processo_id`
- Usar extensão como `Citus` do PostgreSQL
- Complexidade alta, considerar apenas se necessário

---

## ✅ Checklist de Configuração

- [ ] PostgreSQL instalado com version 12+
- [ ] Extensões criadas: `uuid-ossp`, `pg_trgm`, `btree_gin`
- [ ] Schema SQL executado completo
- [ ] Views materializadas criadas
- [ ] postgresql.conf ajustado para RAM/CPU
- [ ] Autovácuo ativado
- [ ] Backups agendados
- [ ] Monitoramento ativo (pg_stat_statements)
- [ ] Triggers criados
- [ ] Dados de teste inseridos
- [ ] Queries testadas com `EXPLAIN ANALYZE`

---

## 📞 Suporte

Para problemas de performance:
1. Coletar logs com `EXPLAIN ANALYZE`
2. Verificar `pg_stat_statements`
3. Confirmar índices foram criados
4. Testar com `VACUUM ANALYZE`
5. Aumentar `work_mem` se necessário

**Contato:** [seu-email@example.com]