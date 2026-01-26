# 🏗️ ARQUITETURA - Spring Boot 4 + PostgreSQL

## Diagrama de Arquitetura

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                             │
│  (Browser, Mobile, Desktop) → REST API JSON                    │
└────────────────────────┬────────────────────────────────────────┘
                         │ HTTP/HTTPS
                         ↓
┌─────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT 4 API                            │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Controller Layer (REST Endpoints)                        │  │
│  │ • ProcessoController  (/api/v1/processos)               │  │
│  │ • PessoaController    (/api/v1/pessoas)                 │  │
│  │ • NotificacaoController (/api/v1/notificacoes)          │  │
│  │ • HealthController    (/api/v1/health)                  │  │
│  └───────────────────────────┬────────────────────────────┘  │
│                              │                                 │
│  ┌───────────────────────────↓────────────────────────────┐  │
│  │ Service Layer (Business Logic)                         │  │
│  │ • ProcessoService      (@Transactional, @Cacheable)    │  │
│  │ • PessoaService        (@Transactional, @Cacheable)    │  │
│  │ • NotificacaoService   (@Transactional)                │  │
│  │ • Exception Handler    (Global Error Handling)         │  │
│  └───────────────────────────┬────────────────────────────┘  │
│                              │                                 │
│  ┌───────────────────────────↓────────────────────────────┐  │
│  │ Repository Layer (Data Access)                         │  │
│  │ • ProcessoRepository    (JpaRepository)                │  │
│  │ • QueixosoRepository    (JpaRepository)                │  │
│  │ • ArguídoRepository     (JpaRepository)                │  │
│  │ • TestemunhaRepository  (JpaRepository)                │  │
│  │ • AvisoRepository       (JpaRepository)                │  │
│  └───────────────────────────┬────────────────────────────┘  │
│                              │                                 │
│  ┌───────────────────────────↓────────────────────────────┐  │
│  │ ORM Layer (Hibernate)                                  │  │
│  │ • Lazy Loading (FetchType.LAZY)                        │  │
│  │ • Batch Processing (size=10)                           │  │
│  │ • Query Optimization                                   │  │
│  │ • Connection Pooling (HikariCP)                        │  │
│  └───────────────────────────┬────────────────────────────┘  │
│                              │                                 │
│  ┌────────────────┬─────────↓──────────┬──────────────────┐  │
│  │ Redis Cache    │   PostgreSQL DB    │   Configuration  │  │
│  │ (TTL: 1h)      │   (JDBC)           │   (application   │  │
│  │                │                     │    .yml)         │  │
│  └────────────────┴─────────────────────┴──────────────────┘  │
└──────────────────────────┬───────────────────────────────────┘
                           │ JDBC
              ┌────────────┴──────────────┐
              │                           │
              ↓                           ↓
    ┌───────────────────┐      ┌──────────────────┐
    │  PostgreSQL 15    │      │    Redis 7       │
    │                   │      │                  │
    │ • 21 Tables       │      │ • Cache Layer    │
    │ • 32 Indexes      │      │ • 5 Cache Names  │
    │ • 5 Views         │      │ • 1h TTL         │
    │ • Partitioning    │      │                  │
    │ • Constraints     │      │ (Optional)       │
    │ • Triggers        │      │                  │
    └───────────────────┘      └──────────────────┘
```

---

## Fluxo de Requisição

```
1. Client Request (curl/browser/app)
   ↓
2. Spring DispatcherServlet
   ↓
3. @RestController (ProcessoController)
   ↓
4. @Service (ProcessoService)
   ├─ @Cacheable? → Redis Cache HIT → Return
   └─ @Cacheable? → Redis Cache MISS ↓
   ↓
5. @Repository (ProcessoRepository)
   ├─ Hibernate generates SQL
   ├─ HikariCP gets connection from pool
   ├─ Execute Query with Indexes
   └─ Return Entity
   ↓
6. MapStruct DTO Mapping
   ↓
7. Jackson JSON Serialization
   ↓
8. HTTP Response 200 OK + JSON Body
   ↓
9. Cache result in Redis (TTL: 1h)
   ↓
10. Client receives JSON response
```

---

## Estrutura de Diretórios (Completa)

```
judicial-system-springboot/
│
├── pom.xml                           (Maven - Java 21, Spring Boot 3.2)
│
├── README.md
│
├── src/
│   ├── main/
│   │   ├── java/com/judicial/system/
│   │   │   ├── JudicialSystemApplication.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── Processo.java
│   │   │   │   ├── TipoProcesso.java
│   │   │   │   ├── Queixoso.java
│   │   │   │   ├── Arguido.java
│   │   │   │   ├── Testemunha.java
│   │   │   │   ├── CapaProcesso.java
│   │   │   │   ├── CartaPrecatoria.java
│   │   │   │   ├── AutosDeclaracao.java
│   │   │   │   ├── AvisoNotificacao.java
│   │   │   │   ├── Magistrado.java
│   │   │   │   ├── Advogado.java
│   │   │   │   └── ... (21 total)
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── ProcessoDTO.java
│   │   │   │   ├── ProcessoCompletoDTO.java
│   │   │   │   ├── QueixosoDTO.java
│   │   │   │   ├── ArguídoDTO.java
│   │   │   │   ├── NotificacaoDTO.java
│   │   │   │   ├── EstatisticaCrimeDTO.java
│   │   │   │   ├── AtividadeUsuarioDTO.java
│   │   │   │   └── TestemunhaDTO.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── ProcessoRepository.java
│   │   │   │   ├── QueixosoRepository.java
│   │   │   │   ├── ArguídoRepository.java
│   │   │   │   ├── TestemunhaRepository.java
│   │   │   │   ├── AvisoNotificacaoRepository.java
│   │   │   │   ├── CapaProcessoRepository.java
│   │   │   │   ├── CartaPrecatoriaRepository.java
│   │   │   │   ├── AutosDeclaracaoRepository.java
│   │   │   │   ├── TipoCrimeRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── TipoDeclaracaoRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── ProcessoService.java
│   │   │   │   │   ├── buscarPorNumero()          (Cached)
│   │   │   │   │   ├── buscarPorId()              (Cached)
│   │   │   │   │   ├── listarProcessosUsuario()
│   │   │   │   │   ├── listarRecentes()
│   │   │   │   │   └── criar()                    (Transactional)
│   │   │   │   │
│   │   │   │   ├── PessoaService.java
│   │   │   │   │   ├── buscarPorBi()              (Cached)
│   │   │   │   │   ├── buscarQueixosos()
│   │   │   │   │   ├── buscarArguidos()
│   │   │   │   │   └── buscarTestemunhas()
│   │   │   │   │
│   │   │   │   ├── NotificacaoService.java
│   │   │   │   │   ├── listarUrgentes()           (Cached)
│   │   │   │   │   ├── listarProximas30Dias()     (Cached)
│   │   │   │   │   └── listarPorArguido()
│   │   │   │   │
│   │   │   │   └── EstatisticasService.java (para futuro)
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── ProcessoController.java        (4 endpoints)
│   │   │   │   ├── PessoaController.java          (4 endpoints)
│   │   │   │   ├── NotificacaoController.java     (3 endpoints)
│   │   │   │   └── HealthController.java          (1 endpoint)
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── RedisConfig.java               (Cache configuration)
│   │   │   │   ├── JpaConfig.java                 (Repository scan)
│   │   │   │   └── SecurityConfig.java            (CORS + Security)
│   │   │   │
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ResourceNotFoundException.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml                    (Main config)
│   │       ├── application-prod.yml               (Production)
│   │       ├── application-test.yml               (Testing)
│   │       └── logback-spring.xml                 (Logging)
│   │
│   └── test/
│       └── java/com/judicial/system/
│           ├── ProcessoServiceTest.java           (JUnit 5)
│           ├── ProcessoControllerIntegrationTest.java
│           ├── PerformanceTest.java
│           └── TestConfiguration.java             (Testcontainers)
│
├── docs/
│   ├── API_REFERENCE.md
│   ├── ARCHITECTURE.md
│   ├── PERFORMANCE.md
│   └── DEPLOYMENT.md
│
├── docker/
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── .dockerignore
│
├── scripts/
│   ├── deploy.sh
│   ├── backup.sh
│   └── restore.sh
│
└── logs/
    └── judicial-system.log
```

---

## Mapeamento de Dados

```
┌─────────────────────────────────────────────────────────┐
│                  REQUISIÇÃO HTTP                        │
│  GET /api/v1/processos/PRO-2025-001                    │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  ProcessoController                      │
│  @GetMapping("/{numero}")                              │
│  buscarPorNumero(String numero)                         │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  ProcessoService                         │
│  @Cacheable(value = "processos", key = "#numero")      │
│  buscarPorNumero(String numero)                         │
│                                                         │
│  1. Verifica Cache (Redis)                             │
│  2. Se HIT: retorna JSON                              │
│  3. Se MISS: vai para Repository                       │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  ProcessoRepository                      │
│  Optional<Processo> findByNumero(String numero)        │
│                                                         │
│  1. Hibernate gera SQL                                 │
│  2. HikariCP pega conexão do pool                      │
│  3. Executa: SELECT * FROM processos WHERE numero = ? │
│  4. Usa índice: idx_processos_numero                   │
│  5. Retorna Entity                                     │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  ProcessoEntity                          │
│  Private fields:                                        │
│  • id: Long                                            │
│  • numero: String                                      │
│  • descricao: String                                   │
│  • tipoProcesso: TipoProcesso (LAZY)                  │
│  • usuario: Usuario (LAZY)                             │
│  • createdAt: LocalDateTime                            │
│  • updatedAt: LocalDateTime                            │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  MapStruct (DTO Mapping)                │
│  Processo Entity → ProcessoCompletoDTO                  │
│                                                         │
│  Hibernate lazy loads:                                 │
│  • TipoProcesso (1 extra query)                        │
│  • Capas, Queixosos, Arguidos, Autos (4 extra)       │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  Jackson (JSON Serialization)            │
│  ProcessoCompletoDTO → JSON String                      │
└────────────┬────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  HTTP Response                          │
│  200 OK                                                 │
│  Content-Type: application/json                        │
│                                                         │
│  {                                                      │
│    "id": 1,                                            │
│    "numero": "PRO-2025-001",                          │
│    "descricao": "...",                                │
│    "tipoProcesso": "Penal",                           │
│    "totalCapas": 2,                                   │
│    "totalQueixosos": 1,                               │
│    "totalArguidos": 3,                                │
│    "totalAutos": 5,                                   │
│    "createdAt": "2025-01-26T10:30:00",               │
│    "updatedAt": "2025-01-26T15:45:30"                │
│  }                                                      │
└─────────────────────────────────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────────────────────┐
│                  Cache Update                           │
│  Redis.set("processos:PRO-2025-001", JSON, TTL=3600)  │
│  (Automático via @Cacheable)                           │
└─────────────────────────────────────────────────────────┘
```

---

## Anotações Spring Boot Utilizadas

```java
// Controllers
@RestController              // Define como REST controller
@RequestMapping("/api/v1")  // Prefix de rotas
@GetMapping("/path")        // Map GET /path
@PostMapping("/path")       // Map POST /path
@PathVariable               // Extract from URL
@RequestParam               // Extract from query string
@RequestBody                // Parse JSON body
@ResponseStatus             // Set HTTP status

// Services
@Service                    // Define como serviço
@Transactional              // Gerencia transações
@Cacheable                  // Cache automático
@CacheEvict                 // Limpar cache
@Async                      // Execução assíncrona
@Timed                      // Métricas

// Repositories
@Repository                 // Define como repository
@Query                      // Custom JPQL queries
@Param                      // Named parameters

// Configuration
@Configuration              // Define classe de config
@Bean                       // Define bean
@EnableCaching              // Ativa cache
@EnableTransactionManagement // Ativa transações

// Data
@Entity                     // Mapeia tabela
@Table                      // Nome da tabela
@Column                     // Mapeamento de coluna
@ManyToOne                  // Relacionamento 1:N
@OneToMany                  // Relacionamento N:1
@JoinColumn                 // Foreign key
@CreationTimestamp          // Preence createdAt
@UpdateTimestamp            // Preence updatedAt
@Id @GeneratedValue         // Primary key auto

// Validation
@NotNull                    // Não nulo
@NotBlank                   // Não vazio
@Size                       // Tamanho
@Email                      // Formato email

// Lombok
@Data                       // Gera getters/setters
@NoArgsConstructor          // Constructor sem args
@AllArgsConstructor         // Constructor com todos
@Builder                    // Pattern builder
@Slf4j                      // Injeta logger
```

---

## Tecnologias & Versões

```
┌─────────────────────────────┬─────────────┐
│ Tecnologia                  │ Versão      │
├─────────────────────────────┼─────────────┤
│ Java                        │ 21 LTS      │
│ Spring Boot                 │ 3.2.0       │
│ Spring Data JPA             │ 3.2.0       │
│ Spring Security             │ 6.2.0       │
│ Hibernate                   │ 6.2.0       │
│ PostgreSQL Driver           │ 42.7.1      │
│ HikariCP                    │ 5.1.0       │
│ Redis/Lettuce               │ 6.3.1       │
│ Jackson                     │ 2.16.0      │
│ OpenAPI/Swagger             │ 2.1.0       │
│ Lombok                      │ 1.18.30     │
│ MapStruct                   │ 1.5.5       │
│ JUnit 5                     │ 5.9.0       │
│ Testcontainers              │ 1.19.3      │
│ Maven                       │ 3.8+        │
└─────────────────────────────┴─────────────┘
```

---

## Resumo da Arquitetura

```
✓ Multi-layer Architecture (Controller → Service → Repository)
✓ Type-safe (Java compilado)
✓ Connection pooling (HikariCP: 5-20 conexões)
✓ ORM (Hibernate com lazy loading)
✓ Cache (Redis: 1h TTL)
✓ Transactional (Spring @Transactional)
✓ REST API (OpenAPI/Swagger documentado)
✓ Error Handling (GlobalExceptionHandler)
✓ Logging (SLF4J + Logback)
✓ Testing (JUnit 5 + Testcontainers)
✓ Performance (< 100ms por request)
✓ Scalability (Horizontal + Vertical)
```

**Pronto para PRODUÇÃO!** 🚀
