# 🏛️ SISTEMA JUDICIAL - Spring Boot 4 Implementation Guide

## 📋 Índice
1. [Setup Inicial](#setup)
2. [Estrutura do Projeto](#estrutura)
3. [Como Executar](#execução)
4. [Endpoints API](#endpoints)
5. [Documentação OpenAPI](#openapi)
6. [Performance](#performance)
7. [Deployment](#deployment)

---

## Setup Inicial {#setup}

### Requisitos
- Java 21+
- Maven 3.8+
- PostgreSQL 12+
- Redis 6+ (opcional mas recomendado)
- Git

### 1. Clonar/Criar Projeto
```bash
# Criar estrutura do projeto
mkdir judicial-system-springboot
cd judicial-system-springboot
```

### 2. Inicializar Maven
```bash
# Copiar pom.xml para raiz do projeto
# Criar estrutura de diretórios
mkdir -p src/main/java/com/judicial/system/{entity,dto,repository,service,controller,config,exception}
mkdir -p src/main/resources
mkdir -p src/test/java
```

### 3. Instalar Dependências
```bash
mvn clean install
```

### 4. Configurar Banco de Dados PostgreSQL
```bash
# Criar banco de dados
psql -U postgres

# SQL
CREATE DATABASE judicial_system ENCODING 'UTF8';
\c judicial_system

# Executar schema SQL
\i /path/to/01_schema_judicial_system.sql
\i /path/to/02_views_queries_optimization.sql
```

### 5. Instalar Redis (Opcional)
```bash
# macOS
brew install redis
brew services start redis

# Linux
sudo apt-get install redis-server
sudo systemctl start redis-server

# Windows (via WSL)
wsl sudo apt-get install redis-server
```

### 6. Configurar application.yml
```yaml
# Editar src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/judicial_system
    username: postgres
    password: sua_senha

  data:
    redis:
      host: localhost
      port: 6379
```

---

## Estrutura do Projeto {#estrutura}

```
judicial-system-springboot/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/judicial/system/
│   │   │   ├── JudicialSystemApplication.java    (Main)
│   │   │   ├── entity/
│   │   │   │   └── Entities.java                 (JPA Entities)
│   │   │   ├── dto/
│   │   │   │   └── DTOs.java                     (Data Transfer Objects)
│   │   │   ├── repository/
│   │   │   │   └── Repositories.java             (Spring Data JPA)
│   │   │   ├── service/
│   │   │   │   └── Services.java                 (Business Logic)
│   │   │   ├── controller/
│   │   │   │   └── Controllers.java              (REST Endpoints)
│   │   │   ├── config/
│   │   │   │   ├── RedisConfig.java
│   │   │   │   ├── JpaConfig.java
│   │   │   │   └── SecurityConfig.java
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
└── README.md
```

---

## Como Executar {#execução}

### Método 1: Maven
```bash
# Compilar
mvn clean compile

# Executar testes
mvn test

# Rodar aplicação
mvn spring-boot:run
```

### Método 2: IDE (IntelliJ/VSCode)
1. Abrir projeto na IDE
2. Maven → Reload Projects
3. Run → Run 'JudicialSystemApplication'

### Método 3: JAR
```bash
# Build
mvn clean package

# Executar
java -jar target/judicial-system-1.0.0.jar
```

### Verificar Startup
```bash
# Aplicação pronta quando ver
# Started JudicialSystemApplication in X.XXX seconds
```

---

## Endpoints API {#endpoints}

### 1. Processos
```bash
# Buscar processo por número
curl -X GET "http://localhost:8080/api/v1/processos/PRO-2025-001" \
  -H "Accept: application/json"

# Buscar processo por ID
curl -X GET "http://localhost:8080/api/v1/processos/id/1"

# Listar processos do usuário
curl -X GET "http://localhost:8080/api/v1/processos/usuario/1?page=0&size=20"

# Listar processos recentes
curl -X GET "http://localhost:8080/api/v1/processos/recentes?dias=30"

# Criar processo
curl -X POST "http://localhost:8080/api/v1/processos" \
  -H "Content-Type: application/json" \
  -d '{
    "numero": "PRO-2025-002",
    "descricao": "Processo teste",
    "tipoProcesso": "Penal"
  }'
```

### 2. Pessoas
```bash
# Buscar por BI
curl -X GET "http://localhost:8080/api/v1/pessoas/bi/123456789LA"

# Buscar queixosos
curl -X GET "http://localhost:8080/api/v1/pessoas/queixosos?nome=Silva&page=0&size=20"

# Buscar arguidos
curl -X GET "http://localhost:8080/api/v1/pessoas/arguidos?nome=João&page=0&size=20"

# Buscar testemunhas
curl -X GET "http://localhost:8080/api/v1/pessoas/testemunhas?nome=Maria&page=0&size=20"
```

### 3. Notificações
```bash
# Listar urgentes
curl -X GET "http://localhost:8080/api/v1/notificacoes/urgentes"

# Listar próximas 30 dias
curl -X GET "http://localhost:8080/api/v1/notificacoes/proximas"

# Notificações de um arguido
curl -X GET "http://localhost:8080/api/v1/notificacoes/arguido/1"
```

### 4. Health Check
```bash
curl -X GET "http://localhost:8080/api/v1/health"

# Response:
{
  "status": "UP",
  "timestamp": "2025-01-26T10:30:00",
  "application": "Judicial System API",
  "version": "1.0.0"
}
```

---

## Documentação OpenAPI {#openapi}

Acesse a documentação interativa em:
```
http://localhost:8080/swagger-ui.html
```

**Recursos:**
- ✓ Teste endpoints diretamente
- ✓ Visualize schemas
- ✓ Veja exemplos de request/response
- ✓ Exporte como OpenAPI spec

### Exemplo Swagger
```yaml
# Download OpenAPI JSON
GET /v3/api-docs

# Compatível com:
- Postman (importe OpenAPI)
- VS Code REST Client
- Insomnia
```

---

## Performance {#performance}

### Connection Pooling (HikariCP)
```yaml
# application.yml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20      # Máx conexões
      minimum-idle: 5            # Mín ociosas
      connection-timeout: 20000  # 20 segundos
```

### Caching com Redis
```java
// Automático via @Cacheable
@Cacheable(value = "processos", key = "#numero")
public Optional<ProcessoDTO> buscarPorNumero(String numero) { ... }

// TTL: 1 hora (configurável)
```

### Lazy Loading
```java
// Evita N+1 queries
@ManyToOne(fetch = FetchType.LAZY)
private TipoProcesso tipoProcesso;
```

### Batch Processing
```java
// Hibernate batch inserts
spring.jpa.properties.hibernate.jdbc.batch_size=10
```

### Query Optimization
```java
// Usa índices criados no SQL
@Query("SELECT p FROM Processo p WHERE p.numero = :numero")
Optional<Processo> findByNumero(@Param("numero") String numero);
```

---

## Testes Unitários

```java
package com.judicial.system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProcessoServiceTest {
    
    @Autowired
    private ProcessoService processoService;
    
    @Test
    void testBuscarPorNumero() {
        // Given
        String numero = "PRO-2025-001";
        
        // When
        var resultado = processoService.buscarPorNumero(numero);
        
        // Then
        assertTrue(resultado.isPresent());
        assertEquals(numero, resultado.get().getNumero());
    }
}
```

---

## Deployment {#deployment}

### 1. Build Production
```bash
mvn clean package -DskipTests -Pprod
```

### 2. Docker (Dockerfile)
```dockerfile
FROM openjdk:21-slim

WORKDIR /app

COPY target/judicial-system-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: judicial_system
      POSTGRES_PASSWORD: senha
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  api:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - postgres
      - redis
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/judicial_system
      SPRING_REDIS_HOST: redis

volumes:
  postgres_data:
```

```bash
# Executar
docker-compose up -d
```

### 3. Variáveis de Ambiente (Produção)
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://db.example.com:5432/judicial
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=$(vault kv get -field=password secret/db)
export SPRING_REDIS_HOST=redis.example.com

java -jar judicial-system.jar
```

---

## Monitoramento

### Métricas
```bash
# Actuator metrics
curl http://localhost:8080/actuator/metrics

# Prometheus format
curl http://localhost:8080/actuator/prometheus
```

### Logs
```bash
# Ver logs
tail -f logs/judicial-system.log

# Procurar erros
grep ERROR logs/judicial-system.log
```

---

## Troubleshooting

### Erro: "Connection to localhost:5432 refused"
```bash
# Verificar PostgreSQL
psql -U postgres -d judicial_system -c "SELECT 1"

# Se não conectar, iniciar
sudo systemctl start postgresql
```

### Erro: "Redis connection refused"
```bash
# Verificar Redis (opcional)
redis-cli ping
# Resposta: PONG

# Se não tiver Redis, comentar no application.yml
# spring.cache.type: none
```

### Erro: "Port 8080 already in use"
```bash
# Mudar porta
export SERVER_PORT=8081

# Ou editar application.yml
server:
  port: 8081
```

### Erro: "Hibernate dialect not found"
```bash
# Verificar driver PostgreSQL no pom.xml
# Recompilar Maven
mvn clean install
```

---

## Performance Esperada

| Operação | Tempo (sem cache) | Tempo (com cache) |
|----------|-------------------|-------------------|
| Buscar processo | 50-100ms | < 10ms |
| Buscar por BI | 20-50ms | < 5ms |
| Buscar por nome | 100-500ms | < 20ms |
| Listar 20 processos | 200-400ms | < 50ms |
| Notificações urgentes | 150-300ms | < 30ms |

---

## Próximos Passos

1. **Autenticação JWT** - Adicionar segurança real
2. **Autorização RBAC** - Controle de acesso por role
3. **Auditoria** - Rastrear mudanças
4. **Backup/Restore** - Automáticos
5. **CI/CD** - GitHub Actions/GitLab CI
6. **Alertas** - Slack/PagerDuty para erros

---

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [OpenAPI 3.0](https://swagger.io/specification/)
- [Redis Documentation](https://redis.io/documentation)
- [PostgreSQL Docs](https://www.postgresql.org/docs/)

---

**Pronto para usar!** 🚀

Se tiver dúvidas, execute:
```bash
mvn spring-boot:run
```

E acesse:
- **API**: http://localhost:8080/api/v1
- **Swagger**: http://localhost:8080/swagger-ui.html
- **Health**: http://localhost:8080/api/v1/health
