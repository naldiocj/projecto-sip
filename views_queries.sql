-- ========================================
-- VIEWS MATERIALIZADAS - PARA DASHBOARD
-- ========================================

-- View 1: Resumo de Processos por Status/Tipo
CREATE MATERIALIZED VIEW mv_processos_resumo AS
SELECT 
    p.id,
    p.numero,
    p.descricao,
    tp.descricao as tipo_processo,
    COUNT(DISTINCT cap.id) as total_capas,
    COUNT(DISTINCT q.id) as total_queixosos,
    COUNT(DISTINCT a.id) as total_arguidos,
    MAX(p.created_at) as data_criacao,
    MAX(p.updated_at) as ultima_atualizacao
FROM processos p
LEFT JOIN tipos_processos tp ON p.tipo_processo_id = tp.id
LEFT JOIN capas_processos cap ON p.id = cap.processo_id
LEFT JOIN queixosos q ON p.id = q.processo_id
LEFT JOIN arguidos a ON p.id = a.processo_id
GROUP BY p.id, p.numero, p.descricao, tp.descricao;

CREATE INDEX idx_mv_processos_numero ON mv_processos_resumo(numero);
CREATE INDEX idx_mv_processos_tipo ON mv_processos_resumo(tipo_processo);

-- View 2: Atividades Recentes por Usuário
CREATE MATERIALIZED VIEW mv_atividades_usuarios AS
SELECT 
    u.id,
    u.nome,
    COUNT(DISTINCT p.id) as total_processos_criados,
    COUNT(DISTINCT CASE WHEN p.created_at > CURRENT_DATE - INTERVAL '30 days' THEN p.id END) as processos_30_dias,
    MAX(p.created_at) as ultima_criacao,
    COUNT(DISTINCT cap.id) as total_capas,
    COUNT(DISTINCT auto.id) as total_autos
FROM usuarios u
LEFT JOIN processos p ON u.usuario_id = p.usuario_id
LEFT JOIN capas_processos cap ON u.usuario_id = cap.usuario_id
LEFT JOIN autos_declaracoes auto ON u.usuario_id = auto.usuario_id
WHERE u.is_active = true
GROUP BY u.id, u.nome;

CREATE INDEX idx_mv_atividades_usuario ON mv_atividades_usuarios(nome);

-- View 3: Crimes Mais Comuns
CREATE MATERIALIZED VIEW mv_crimes_estatisticas AS
SELECT 
    tc.id,
    tc.artigo_penal,
    tc.descricao,
    COUNT(DISTINCT cap.id) as total_casos,
    COUNT(DISTINCT a.id) as total_arguidos,
    COUNT(DISTINCT q.id) as total_queixosos
FROM tipos_crimes tc
LEFT JOIN capas_processos cap ON tc.id = cap.crime_id
LEFT JOIN arguidos a ON cap.id = a.processo_id
LEFT JOIN queixosos q ON cap.id = q.processo_id
GROUP BY tc.id, tc.artigo_penal, tc.descricao
ORDER BY total_casos DESC;

CREATE INDEX idx_mv_crimes_total ON mv_crimes_estatisticas(total_casos DESC);

-- View 4: Peritagens por Especialidade/Tipo
CREATE MATERIALIZED VIEW mv_peritos_carregamento AS
SELECT 
    pre.nome,
    COUNT(DISTINCT ara.id) as reconstituicoes_asignadas,
    COUNT(DISTINCT ped.exame_directo_id) as exames_asignados,
    MAX(ara.created_at) as ultima_atividade
FROM peritos_reconstituicoes pre
LEFT JOIN autos_reconstituicoes_arguidos ara ON pre.reconstituicoes_arguidos_id = ara.id
LEFT JOIN peritos_exames_directos ped ON pre.nome = ped.nome
GROUP BY pre.nome;

-- View 5: Notificações Pendentes
CREATE MATERIALIZED VIEW mv_notificacoes_pendentes AS
SELECT 
    an.id,
    an.numero_processo,
    an.numero_folha,
    arg.nome as arguido_nome,
    an.data_comparencia,
    an.visto_director,
    u.nome as instructor_nome,
    CASE 
        WHEN an.data_comparencia < CURRENT_DATE THEN 'EXPIRADO'
        WHEN an.data_comparencia <= CURRENT_DATE + INTERVAL '7 days' THEN 'URGENTE'
        WHEN an.data_comparencia <= CURRENT_DATE + INTERVAL '30 days' THEN 'PROXIMO'
        ELSE 'PLANEJADO'
    END as status_urgencia
FROM avisos_notificacoes an
LEFT JOIN arguidos arg ON an.arguido_id = arg.id
LEFT JOIN usuarios u ON an.instructor_id = u.id
WHERE an.visto_director = false
ORDER BY an.data_comparencia ASC;

CREATE INDEX idx_mv_notificacoes_data ON mv_notificacoes_pendentes(data_comparencia);
CREATE INDEX idx_mv_notificacoes_status ON mv_notificacoes_pendentes(status_urgencia);

-- ========================================
-- QUERIES OTIMIZADAS COM EXPLAIN ANALYZE
-- ========================================

-- 1. Buscar processo por número (usa índice simples)
EXPLAIN ANALYZE
SELECT 
    p.id,
    p.numero,
    p.descricao,
    tp.descricao as tipo,
    COUNT(DISTINCT cap.id) as num_capas
FROM processos p
LEFT JOIN tipos_processos tp ON p.tipo_processo_id = tp.id
LEFT JOIN capas_processos cap ON p.id = cap.processo_id
WHERE p.numero = 'PRO-2025-001'
GROUP BY p.id, p.numero, p.descricao, tp.descricao;

-- 2. Buscar queixosos por nome (usa índice GIN)
EXPLAIN ANALYZE
SELECT 
    q.id,
    q.nome,
    q.numero_bi,
    e.bairro,
    COUNT(DISTINCT p.id) as total_processos
FROM queixosos q
LEFT JOIN enderecos e ON q.endereco_id = e.id
LEFT JOIN processos p ON q.processo_id = p.id
WHERE q.nome ILIKE '%Silva%'
GROUP BY q.id, q.nome, q.numero_bi, e.bairro
LIMIT 50;

-- 3. Listar processos de um instrutor com autos recentes
EXPLAIN ANALYZE
SELECT 
    p.numero,
    p.descricao,
    cap.numero_processo,
    COUNT(DISTINCT auto.id) as total_autos,
    MAX(auto.created_at) as ultimo_auto
FROM processos p
JOIN capas_processos cap ON p.id = cap.processo_id
LEFT JOIN autos_declaracoes auto ON p.id = auto.processo_id
WHERE cap.instructor_id = 1
    AND p.created_at >= CURRENT_DATE - INTERVAL '90 days'
GROUP BY p.numero, p.descricao, cap.numero_processo
ORDER BY MAX(auto.created_at) DESC
LIMIT 100;

-- 4. Encontrar arguidos por BI (busca exata - índice simples)
EXPLAIN ANALYZE
SELECT 
    a.id,
    a.nome,
    a.numero_bi,
    a.data_emissao_bi,
    COUNT(DISTINCT p.id) as processos_envolvido,
    COUNT(DISTINCT auto.id) as total_autos
FROM arguidos a
LEFT JOIN processos p ON a.processo_id = p.id
LEFT JOIN autos_interrogatorios_arguidos auto ON a.id = auto.arguido_id
WHERE a.numero_bi = '123456789LA'
GROUP BY a.id, a.nome, a.numero_bi, a.data_emissao_bi;

-- 5. Notificações com vencimento próximo (índice composto)
EXPLAIN ANALYZE
SELECT 
    an.id,
    an.numero_folha,
    arg.nome,
    an.data_comparencia,
    u.nome as instructor,
    e.bairro
FROM avisos_notificacoes an
JOIN arguidos arg ON an.arguido_id = arg.id
JOIN usuarios u ON an.instructor_id = u.id
LEFT JOIN enderecos e ON an.endereco_destino_id = e.id
WHERE an.visto_director = false
    AND an.data_comparencia BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7 days'
ORDER BY an.data_comparencia ASC;

-- 6. Estatísticas de crimes por período
EXPLAIN ANALYZE
SELECT 
    tc.artigo_penal,
    tc.descricao as crime,
    COUNT(DISTINCT cap.id) as total_casos,
    TO_CHAR(DATE_TRUNC('month', cap.data_ocorrencia), 'YYYY-MM') as mes
FROM tipos_crimes tc
LEFT JOIN capas_processos cap ON tc.id = cap.crime_id
WHERE cap.data_ocorrencia >= CURRENT_DATE - INTERVAL '12 months'
GROUP BY tc.artigo_penal, tc.descricao, DATE_TRUNC('month', cap.data_ocorrencia)
ORDER BY mes DESC, total_casos DESC;

-- 7. Busca de processo com todos os dados relacionados (JOIN complexo otimizado)
EXPLAIN ANALYZE
SELECT 
    p.id as processo_id,
    p.numero,
    tp.descricao as tipo_processo,
    cap.numero_expediente,
    cap.numero_processo,
    arg.nome as arguido_nome,
    q.nome as queixoso_nome,
    tc.descricao as crime,
    m.nome_completo as magistrado,
    u.nome as usuario_criador,
    COUNT(DISTINCT auto.id) as total_autos
FROM processos p
LEFT JOIN tipos_processos tp ON p.tipo_processo_id = tp.id
LEFT JOIN capas_processos cap ON p.id = cap.processo_id
LEFT JOIN arguidos arg ON cap.processo_id = arg.processo_id
LEFT JOIN queixosos q ON cap.queixoso_id = q.id
LEFT JOIN tipos_crimes tc ON cap.crime_id = tc.id
LEFT JOIN magistrados m ON cap.magistrado_id = m.id
LEFT JOIN usuarios u ON p.usuario_id = u.usuario_id
LEFT JOIN autos_declaracoes auto ON p.id = auto.processo_id
WHERE p.id = 1
GROUP BY p.id, p.numero, tp.descricao, cap.numero_expediente, cap.numero_processo,
         arg.nome, q.nome, tc.descricao, m.nome_completo, u.nome;

-- 8. Listar testemunhas por processo com informações de contato
EXPLAIN ANALYZE
SELECT 
    t.id,
    t.nome,
    t.numero_bi,
    t.telefone,
    t.email,
    e.bairro,
    e.rua,
    COUNT(DISTINCT auto.id) as depoimentos_realizados
FROM testemunhas t
LEFT JOIN enderecos e ON t.endereco_id = e.id
LEFT JOIN autos_depoimentos_directos auto ON t.id = auto.processo_id
WHERE t.processo_id = 1
GROUP BY t.id, t.nome, t.numero_bi, t.telefone, t.email, e.bairro, e.rua
ORDER BY t.nome ASC;

-- ========================================
-- QUERIES PARA MONITORAMENTO E HEALTH CHECK
-- ========================================

-- Verificar índices não utilizados
SELECT 
    schemaname,
    tablename,
    indexname,
    idx_scan,
    idx_blks_read,
    pg_size_pretty(pg_relation_size(indexrelname::regclass)) as size
FROM pg_stat_user_indexes
WHERE idx_scan = 0 AND schemaname = 'public'
ORDER BY pg_relation_size(indexrelname::regclass) DESC;

-- Ver tamanho das tabelas
SELECT 
    schemaname,
    tablename,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename)) as total_size,
    pg_size_pretty(pg_relation_size(schemaname||'.'||tablename)) as table_size,
    pg_size_pretty(pg_total_relation_size(schemaname||'.'||tablename) - pg_relation_size(schemaname||'.'||tablename)) as indexes_size
FROM pg_tables
WHERE schemaname = 'public'
ORDER BY pg_total_relation_size(schemaname||'.'||tablename) DESC;

-- Queries lentas
SELECT 
    query,
    calls,
    total_time,
    mean_time,
    max_time,
    rows
FROM pg_stat_statements
WHERE mean_time > 100
ORDER BY total_time DESC
LIMIT 20;

-- Conexões ativas
SELECT 
    datname,
    usename,
    state,
    COUNT(*) as connection_count,
    MAX(query_start) as oldest_query
FROM pg_stat_activity
WHERE datname IS NOT NULL
GROUP BY datname, usename, state
ORDER BY connection_count DESC;

-- Verificar saúde do disco (tamanho do banco)
SELECT 
    pg_size_pretty(pg_database_size(current_database())) as database_size;

-- ========================================
-- SCRIPT DE MANUTENÇÃO PERIÓDICA
-- ========================================

-- Executar diariamente ou semanalmente
VACUUM ANALYZE;

-- Reindex de tabelas fragmentadas (mensal)
-- REINDEX DATABASE seu_banco_dados;

-- Atualizar views materializadas (pode ser agendado)
-- REFRESH MATERIALIZED VIEW CONCURRENTLY mv_processos_resumo;
-- REFRESH MATERIALIZED VIEW CONCURRENTLY mv_atividades_usuarios;
-- REFRESH MATERIALIZED VIEW CONCURRENTLY mv_crimes_estatisticas;
-- REFRESH MATERIALIZED VIEW CONCURRENTLY mv_notificacoes_pendentes;