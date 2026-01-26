# 📊 MODELO ENTIDADE-RELACIONAMENTO (ER) - SISTEMA JUDICIAL

## 1. DIAGRAMA ER CONCEITUAL

```
┌─────────────────────────────────────────────────────────────────────┐
│                    SISTEMA DE GESTÃO PROCESSUAL                    │
│                   MODELO ENTIDADE-RELACIONAMENTO                    │
└─────────────────────────────────────────────────────────────────────┘

                              USUARIOS
                            ┌─────────┐
                            │   id    │◄─────────┐
                            │  nome   │          │
                            │usuarioId│          │
                            └────┬────┘          │
                                 │               │
                ┌────────────────┼───────────────┼──────────┐
                │                │               │          │
                ▼                ▼               ▼          ▼
          ┌──────────┐    ┌──────────┐    ┌──────────┐  ┌──────────┐
          │ PROCESSOS│    │ QUEIXOSOS│    │ ARGUIDOS │  │MAGISTRADOS
          │────────  │    │────────  │    │──────────│  │──────────
          │ id (PK)  │    │ id (PK)  │    │ id (PK) │  │ id (PK)
          │ numero   │    │ nome     │    │ nome    │  │ nomeCompleto
          │ descricao│    │ numeroBi │    │ numeroBi│  │ especialidade
          │ created_at├───┤ email    │    │ email   │  └──────────┘
          └────┬─────┘    │ telefone │    │ telefone│
               │          │ endereco_id   │ endereco_id
               │          │ processo_id   │ processo_id
               │          └────┬────┘    └────┬────┘
               │               │             │
        ┌──────┴────────────────┴─────────────┴─────────────┐
        │      TIPOS_PROCESSOS                              │
        │  ┌─────────────┐                                  │
        │  │ id (PK)     │                                  │
        │  │ descricao   │                                  │
        │  │ createdAt   │                                  │
        │  └─────────────┘                                  │
        │                                                   │
        │      CAPAS_PROCESSOS                              │
        │  ┌─────────────────┐                              │
        └─►│ id (PK)         │                              │
           │ numeroProcesso  │                              │
           │ numeroExpediente│                              │
           │ dataOcorrencia  │                              │
           │ endereco        │                              │
           │ processo_id (FK)│                              │
           │ magistrado_id   │                              │
           │ crime_id        │                              │
           │ queixoso_id     │                              │
           │ livroRegistro_id│                              │
           └────┬────────────┘                              │
                │                                          │
                │      ┌──────────────────┐               │
                └─────►│ TIPOS_CRIMES     │               │
                       │────────────────  │               │
                       │ id (PK)          │               │
                       │ artigoPenal      │               │
                       │ descricao        │               │
                       │ penaMinima       │               │
                       │ penaMaxima       │               │
                       └──────────────────┘               │
                                                         │
        ┌────────────────────────────────────────────────┘
        │
        ▼
   ┌──────────────┐
   │  ENDERECOS   │
   │────────────  │
   │ id (PK)      │
   │ provincia    │
   │ municipio    │
   │ distrito     │
   │ bairro       │
   │ rua          │
   │ casa         │
   └──────────────┘


   RELACIONAMENTOS ENTRE AUTOS (DOCUMENTOS)
   
   ┌──────────────┐      ┌─────────────────┐
   │   PROCESSOS  │◄─────┤AUTOS_DECLARACOES│
   │              │      │                 │
   │   1  : N     │      │ id (PK)         │
   └──────────────┘      │ numero_folha    │
                         │ data_ocorrencia │
                         │ instructor_id   │
                         │ queixoso_id     │
                         │ advogado_id     │
                         │ tipo_decl_id    │
                         │ processo_id(FK) │
                         └─────────────────┘

   ┌──────────────┐      ┌─────────────────┐
   │   PROCESSOS  │◄─────┤AUTOS_ACARIACOES │
   │              │      │                 │
   │   1  : N     │      │ id (PK)         │
   └──────────────┘      │ numero_folha    │
                         │ data_ocorrencia │
                         │ instructor_id   │
                         │ queixoso_id     │
                         │ arguido_id      │
                         │ advogado_id     │
                         │ processo_id(FK) │
                         └─────────────────┘

   ┌──────────────┐      ┌─────────────────────────────┐
   │   PROCESSOS  │◄─────┤AUTOS_INTERROGATORIOS_ARGUIDOS
   │              │      │                             │
   │   1  : N     │      │ id (PK)                     │
   └──────────────┘      │ numero_folha                │
                         │ data_ocorrencia             │
                         │ defensor_oficioso           │
                         │ instructor_id               │
                         │ arguido_id                  │
                         │ materia_autos               │
                         │ processo_id(FK)             │
                         └─────────────────────────────┘

   ┌──────────────┐      ┌──────────────────────────────┐
   │   PROCESSOS  │◄─────┤AUTOS_RECONSTITUICOES_ARGUIDOS│
   │              │      │                              │
   │   1  : N     │      │ id (PK)                      │
   └──────────────┘      │ numero_folha                 │
                         │ data_ocorrencia              │
                         │ instructor_id                │
                         │ arguido_id                   │
                         │ tipo_crime_id                │
                         │ advogado_id                  │
                         │ meios_utilizados             │
                         │ processo_id(FK)              │
                         └──────────────────────────────┘

   ┌──────────────┐      ┌──────────────────────────┐
   │   PROCESSOS  │◄─────┤AVISOS_NOTIFICACOES       │
   │              │      │                          │
   │   1  : N     │      │ id (PK)                  │
   └──────────────┘      │ numero_folha             │
                         │ arguido_id               │
                         │ data_emissao             │
                         │ data_comparencia         │
                         │ instructor_id            │
                         │ endereco_destino_id      │
                         │ na_qualidade_de_id       │
                         │ visto_director           │
                         │ processo_id(FK)          │
                         └──────────────────────────┘
```

---

## 2. TABELA DE ENTIDADES

| Entidade | Descrição | Tipo | Chave |
|----------|-----------|------|-------|
| USUARIOS | Usuários do sistema | Principal | id |
| PROCESSOS | Processos judiciais | Principal | id |
| TIPOS_PROCESSOS | Categorias de processos | Referência | id |
| CAPAS_PROCESSOS | Capa/abertura de processo | Principal | id |
| QUEIXOSOS | Pessoas que abrem queixa | Principal | id |
| ARGUIDOS | Pessoas acusadas | Principal | id |
| TESTEMUNHAS | Testemunhas de processo | Principal | id |
| MAGISTRADOS | Juízes/magistrados | Principal | id |
| ADVOGADOS | Advogados | Principal | id |
| TIPOS_ADVOGADOS | Categorias de advogados | Referência | id |
| TIPOS_CRIMES | Tipos de crime | Referência | id |
| ENDERECOS | Endereços | Principal | id |
| LIVROS_REGISTOS | Livros de registro | Principal | id |
| AUTOS_DECLARACOES | Autos declarações | Documento | id |
| AUTOS_ACARIACOES | Autos acariações | Documento | id |
| AUTOS_ADITAMENTOS | Autos aditamentos | Documento | id |
| AUTOS_INTERROGATORIOS_ARGUIDOS | Interrogatórios | Documento | id |
| AUTOS_RECONSTITUICOES_ARGUIDOS | Reconstituições | Documento | id |
| AUTOS_RECONSTITUICOES | Reconstituições | Documento | id |
| AUTOS_EXAMES_DIRECTOS | Exames diretos | Documento | id |
| AUTOS_EXAMES_DIRECTOS_AVALIACOES | Avaliações de exames | Documento | id |
| AUTOS_DEPOIMENTOS_DIRECTOS | Depoimentos diretos | Documento | id |
| AUTOS_RECONHECIMENTO_FISCO_OBJECTOS | Reconhecimento de objetos | Documento | id |
| AUTOS_RECONHECIMENTO_FISCO_PESSOAS | Reconhecimento de pessoas | Documento | id |
| CARTAS_PRECATORIAS | Cartas precatórias | Documento | id |
| REMESSA_CARTAS_PRECATORIAS | Remessas de cartas | Documento | id |
| TERMOS_ENTREGAS | Termos de entrega | Documento | id |
| PEDIDOS_COMPARENCIAS | Pedidos de comparência | Documento | id |
| AVISOS_NOTIFICACOES | Avisos e notificações | Documento | id |
| TIPOS_DECLARACOES | Tipos de declaração | Referência | id |
| TIPOS_QUALIDADES | Qualidades no processo | Referência | id |
| PERITOS_RECONSTITUICOES | Peritos em reconstituições | Suporte | id |
| PERITOS_EXAMES_DIRECTOS | Peritos em exames | Suporte | id |
| PERITOS_EXAMES_DIRECTOS_AVALIACOES | Peritos em avaliações | Suporte | id |

---

## 3. RELACIONAMENTOS PRINCIPAIS

### Cardinalidade

| Relacionamento | Cardinalidade | Descrição |
|---|---|---|
| USUARIO : PROCESSOS | 1 : N | Um usuário cria muitos processos |
| TIPO_PROCESSO : PROCESSOS | 1 : N | Um tipo tem muitos processos |
| PROCESSO : CAPAS_PROCESSOS | 1 : N | Um processo tem uma ou mais capas |
| PROCESSO : QUEIXOSOS | 1 : N | Um processo envolve muitos queixosos |
| PROCESSO : ARGUIDOS | 1 : N | Um processo envolve muitos arguidos |
| PROCESSO : AUTOS | 1 : N | Um processo gera muitos autos |
| QUEIXOSO : ENDERECO | N : 1 | Muitos queixosos podem ter um endereço |
| ARGUIDO : ENDERECO | N : 1 | Muitos arguidos podem ter um endereço |
| TESTEMUNHA : ENDERECO | N : 1 | Muitos testemunhas podem ter um endereço |
| MAGISTRADO : CAPAS_PROCESSOS | 1 : N | Um magistrado instrui muitas capas |
| ADVOGADO : TIPO_ADVOGADO | N : 1 | Muitos advogados tem um tipo |
| TIPO_CRIME : CAPAS_PROCESSOS | 1 : N | Um crime está em muitas capas |
| LIVRO_REGISTRO : CAPAS_PROCESSOS | 1 : N | Um livro tem muitas capas registadas |

---

## 4. DIAGRAMA RELACIONAL (LÓGICO)

```
┌─────────────────────────────────────────────────────────────────┐
│ USUARIOS                                                        │
├─────────────────────────────────────────────────────────────────┤
│ id (PK)                                                         │
│ nome (VARCHAR 255)                                              │
│ usuario_id (INT UNIQUE)                                         │
│ created_at (TIMESTAMP)                                          │
│ updated_at (TIMESTAMP)                                          │
│ is_active (BOOLEAN)                                             │
└─────────────────────────────────────────────────────────────────┘
       ▲
       │ FK: usuario_id
       │
┌──────┴──────────────────────────────────────────────────────────┐
│                                                                 │
│  ┌──────────────────────┐    ┌──────────────────────┐          │
│  │ TIPOS_PROCESSOS      │    │ PROCESSOS            │          │
│  ├──────────────────────┤    ├──────────────────────┤          │
│  │ id (PK)              │◄───│ id (PK)              │          │
│  │ descricao (VARCHAR)  │    │ numero (VARCHAR)     │          │
│  │ created_at           │    │ descricao (TEXT)     │          │
│  │ updated_at           │    │ tipo_processo_id(FK) │          │
│  └──────────────────────┘    │ usuario_id (FK)      │          │
│                               │ created_at           │          │
│                               │ updated_at           │          │
│                               └──────────┬───────────┘          │
│                                          │                     │
│                    ┌─────────────────────┼──────────────────┐  │
│                    │                     │                  │  │
│  ┌────────────────────────────────┐   ┌──┴─────────────┐  │  │
│  │ CAPAS_PROCESSOS                │   │ QUEIXOSOS      │  │  │
│  ├────────────────────────────────┤   ├────────────────┤  │  │
│  │ id (PK)                        │   │ id (PK)        │  │  │
│  │ ano (VARCHAR 4)                │   │ nome (VARCHAR) │  │  │
│  │ numero_processo (VARCHAR)      │   │ numeroBi       │  │  │
│  │ numero_expediente (VARCHAR)    │   │ email          │  │  │
│  │ data_ocorrencia (TIMESTAMP)    │   │ telefone       │  │  │
│  │ endereco (VARCHAR)             │   │ endereco_id    │  │  │
│  │ descricao (TEXT)               │   │ processo_id    │  │  │
│  │ magistrado_id (FK) ────────┐   │   │ usuario_id     │  │  │
│  │ instructor_id (FK)         │   │   │ created_at     │  │  │
│  │ crime_id (FK) ────────┐    │   │   │ updated_at     │  │  │
│  │ queixoso_id (FK) ─────┼────┼───┤   └────────────────┘  │  │
│  │ livro_registo_id (FK) │    │   │                       │  │
│  │ processo_id (FK) ─────┼────┘   │   ┌──────────────────┐ │  │
│  │ usuario_id (FK)       │        │   │ ARGUIDOS         │ │  │
│  │ created_at            │        │   ├──────────────────┤ │  │
│  │ updated_at            │        │   │ id (PK)          │ │  │
│  └────────────────┬───────────────┼───┤ nome (VARCHAR)   │ │  │
│                   │               │   │ numeroBi         │ │  │
│                   │               │   │ email            │ │  │
│  ┌────────────────┴──────────┐    │   │ telefone         │ │  │
│  │                           │    │   │ endereco_id      │ │  │
│  │  ┌──────────────────────┐ │    │   │ processo_id      │ │  │
│  │  │ MAGISTRADOS          │ │    │   │ usuario_id       │ │  │
│  │  ├──────────────────────┤ │    │   │ created_at       │ │  │
│  │  │ id (PK)              │ │    │   │ updated_at       │ │  │
│  │  │ nomeCompleto         │◄┴────┤   └──────────────────┘ │  │
│  │  │ especialidade        │      │                        │  │
│  │  │ usuario_id (FK)      │      │   ┌──────────────────┐ │  │
│  │  └──────────────────────┘      │   │ TIPOS_CRIMES     │ │  │
│  │                                │   ├──────────────────┤ │  │
│  │  ┌──────────────────────┐      │   │ id (PK)          │ │  │
│  │  │ TIPOS_CRIMES         │      │   │ artigoPenal      │ │  │
│  │  ├──────────────────────┤      │   │ descricao (TEXT) │ │  │
│  │  │ id (PK)              │◄─────┤   │ usuario_id (FK)  │ │  │
│  │  │ artigoPenal          │      │   │ created_at       │ │  │
│  │  │ descricao (TEXT)     │      │   │ updated_at       │ │  │
│  │  │ usuario_id (FK)      │      │   └──────────────────┘ │  │
│  │  │ created_at           │      │                        │  │
│  │  │ updated_at           │      │   ┌──────────────────┐ │  │
│  │  └──────────────────────┘      └───┤ ENDERECOS        │ │  │
│  │                                    ├──────────────────┤ │  │
│  │                                    │ id (PK)          │ │  │
│  │                                    │ provincia        │ │  │
│  │                                    │ municipio        │ │  │
│  │                                    │ bairro           │ │  │
│  │                                    │ rua              │ │  │
│  │                                    │ usuario_id (FK)  │ │  │
│  │                                    │ created_at       │ │  │
│  │                                    └──────────────────┘ │  │
│  └────────────────────────────────────────────────────────┘  │
│                                                               │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │ AVISOS_NOTIFICACOES                                      │ │
│  ├──────────────────────────────────────────────────────────┤ │
│  │ id (PK)                                                  │ │
│  │ numero_folha (VARCHAR)                                   │ │
│  │ arguido_id (FK) ──────────┐                              │ │
│  │ numero_processo (INT)     │                              │ │
│  │ data_emissao (TIMESTAMP)  │                              │ │
│  │ data_comparencia (TIMESTAMP)                             │ │
│  │ endereco_destino_id (FK)  │                              │ │
│  │ na_qualidade_de_id (FK)   │                              │ │
│  │ instructor_id (FK) ────┐  │                              │ │
│  │ visto_director (BOOLEAN)  │                              │ │
│  │ processo_id (FK) ────────┘                               │ │
│  │ usuario_id (FK)           │                              │ │
│  │ created_at                │                              │ │
│  │ updated_at                │                              │ │
│  └────────────────┬──────────┴──────────────────────────────┘ │
│                   │                                           │
│  ┌────────────────┴───────────────────────────────────────┐   │
│  │                                                        │   │
│  │  ┌───────────────────────────────────────────┐        │   │
│  │  │ AUTOS_DECLARACOES                         │        │   │
│  │  ├───────────────────────────────────────────┤        │   │
│  │  │ id (PK)                                   │        │   │
│  │  │ numero_folha (VARCHAR)                    │        │   │
│  │  │ data_ocorrencia (TIMESTAMP)                │        │   │
│  │  │ endereco (VARCHAR)                        │        │   │
│  │  │ descricao (TEXT)                          │        │   │
│  │  │ instructor_id (FK)                        │        │   │
│  │  │ queixoso_id (FK)                          │        │   │
│  │  │ advogado_id (FK)                          │        │   │
│  │  │ tipo_declaracao_id (FK)                   │        │   │
│  │  │ materia_autos (VARCHAR)                   │        │   │
│  │  │ processo_id (FK) ─────────────────────────┼────────┘   │
│  │  │ usuario_id (FK)                           │        │   │
│  │  │ created_at                                │        │   │
│  │  │ updated_at                                │        │   │
│  │  └───────────────────────────────────────────┘        │   │
│  │                                                        │   │
│  │  ┌───────────────────────────────────────────┐        │   │
│  │  │ AUTOS_ACARIACOES                          │        │   │
│  │  ├───────────────────────────────────────────┤        │   │
│  │  │ id (PK)                                   │        │   │
│  │  │ numero_folha (VARCHAR)                    │        │   │
│  │  │ data_ocorrencia (TIMESTAMP)                │        │   │
│  │  │ endereco_id (FK)                          │        │   │
│  │  │ descricao (TEXT)                          │        │   │
│  │  │ instructor_id (FK)                        │        │   │
│  │  │ queixoso_id (FK)                          │        │   │
│  │  │ arguido_id (FK)                           │        │   │
│  │  │ advogado_id (FK)                          │        │   │
│  │  │ materia_autos (VARCHAR)                   │        │   │
│  │  │ processo_id (FK) ─────────────────────────┼────────┘   │
│  │  │ usuario_id (FK)                           │            │
│  │  │ created_at                                │            │
│  │  │ updated_at                                │            │
│  │  └───────────────────────────────────────────┘            │
│  │                                                        │   │
│  │  ┌───────────────────────────────────────────┐        │   │
│  │  │ AUTOS_INTERROGATORIOS_ARGUIDOS            │        │   │
│  │  ├───────────────────────────────────────────┤        │   │
│  │  │ id (PK)                                   │        │   │
│  │  │ numero_folha (VARCHAR)                    │        │   │
│  │  │ data_ocorrencia (TIMESTAMP)                │        │   │
│  │  │ endereco_id (FK)                          │        │   │
│  │  │ defensor_oficioso (VARCHAR)                │        │   │
│  │  │ instructor_id (FK)                        │        │   │
│  │  │ arguido_id (FK)                           │        │   │
│  │  │ materia_autos (VARCHAR)                   │        │   │
│  │  │ processo_id (FK) ─────────────────────────┼────────┘   │
│  │  │ usuario_id (FK)                           │            │
│  │  │ created_at                                │            │
│  │  │ updated_at                                │            │
│  │  └───────────────────────────────────────────┘            │
│  │                                                        │   │
│  │  ┌───────────────────────────────────────────┐        │   │
│  │  │ AUTOS_RECONSTITUICOES_ARGUIDOS            │        │   │
│  │  ├───────────────────────────────────────────┤        │   │
│  │  │ id (PK)                                   │        │   │
│  │  │ numero_folha (VARCHAR)                    │        │   │
│  │  │ data_ocorrencia (TIMESTAMP)                │        │   │
│  │  │ endereco_id (FK)                          │        │   │
│  │  │ instructor_id (FK)                        │        │   │
│  │  │ tipo_crime_id (FK)                        │        │   │
│  │  │ arguido_id (FK)                           │        │   │
│  │  │ numero_processo (INT)                     │        │   │
│  │  │ advogado_id (FK)                          │        │   │
│  │  │ defensor_oficioso (VARCHAR)                │        │   │
│  │  │ meios_utilizados (TEXT)                   │        │   │
│  │  │ descricao (TEXT)                          │        │   │
│  │  │ processo_id (FK) ─────────────────────────┼────────┘   │
│  │  │ usuario_id (FK)                           │            │
│  │  │ created_at                                │            │
│  │  │ updated_at                                │            │
│  │  └───────────────────────────────────────────┘            │
│  │                                                        │   │
│  │  ┌───────────────────────────────────────────┐        │   │
│  │  │ TERMOS_ENTREGAS                           │        │   │
│  │  ├───────────────────────────────────────────┤        │   │
│  │  │ id (PK)                                   │        │   │
│  │  │ visto_director (BOOLEAN)                  │        │   │
│  │  │ data_ocorrencia (TIMESTAMP)                │        │   │
│  │  │ endereco_id (FK)                          │        │   │
│  │  │ instructor_id (FK)                        │        │   │
│  │  │ queixoso_id (FK)                          │        │   │
│  │  │ arguido_id (FK)                           │        │   │
│  │  │ despacho_magistrado (TEXT)                │        │   │
│  │  │ numero_processo (VARCHAR)                 │        │   │
│  │  │ numero_folha (VARCHAR)                    │        │   │
│  │  │ artigo_apreendido (TEXT)                  │        │   │
│  │  │ data_entrega (DATE)                       │        │   │
│  │  │ processo_id (FK) ─────────────────────────┼────────┘   │
│  │  │ usuario_id (FK)                           │            │
│  │  │ created_at                                │            │
│  │  │ updated_at                                │            │
│  │  └───────────────────────────────────────────┘            │
│  │                                                            │
│  │  ┌───────────────────────────────────────────┐            │
│  │  │ PEDIDOS_COMPARENCIAS                      │            │
│  │  ├───────────────────────────────────────────┤            │
│  │  │ id (PK)                                   │            │
│  │  │ numero_numero (BOOLEAN)                   │            │
│  │  │ assunto (VARCHAR)                         │            │
│  │  │ numero_processo (VARCHAR)                 │            │
│  │  │ crime_id (FK)                             │            │
│  │  │ endereco_id (FK)                          │            │
│  │  │ data_comparencia (TIMESTAMP)              │            │
│  │  │ na_qualidade_de_id (FK)                   │            │
│  │  │ arguido_id (FK)                           │            │
│  │  │ testemunha_id (FK)                        │            │
│  │  │ instructor_id (FK)                        │            │
│  │  │ materia_autos (VARCHAR)                   │            │
│  │  │ processo_id (FK) ─────────────────────────┼────────────┘
│  │  │ usuario_id (FK)                           │
│  │  │ created_at                                │
│  │  │ updated_at                                │
│  │  └───────────────────────────────────────────┘
│  │
│  └────────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────┘
```

---

## 5. NORMAS APLICADAS

### Primeira Forma Normal (1FN)
✅ Todos os atributos são atômicos (indivisíveis)
✅ Não há repetição de grupos
✅ Cada célula contém um único valor

### Segunda Forma Normal (2FN)
✅ Está em 1FN
✅ Todos os atributos não-chave dependem totalmente da chave primária
✅ Sem dependências parciais

### Terceira Forma Normal (3FN)
✅ Está em 2FN
✅ Sem dependências transitivas
✅ Exemplo: `descricao_tipo_crime` não está em PROCESSOS, mas em TIPOS_CRIMES

### Forma Normal de Boyce-Codd (FNBC)
✅ Para relacionamentos mais complexos
✅ Garante que toda determinante é uma chave candidata

---

## 6. ATRIBUTOS ESPECIAIS

### Chaves Primárias
- Todas as entidades têm `id` (BIGSERIAL PRIMARY KEY)
- Garante unicidade e identifica cada registro

### Chaves Estrangeiras (Foreign Keys)
- Mantêm integridade referencial
- Exemplo: `processo_id` em AUTOS_DECLARACOES referencia PROCESSOS.id

### Atributos de Auditoria
- `created_at`: Timestamp de criação (automático)
- `updated_at`: Timestamp de atualização (automático com trigger)

### Índices
- Chaves primárias: índice automático
- Chaves estrangeiras: índices para JOINs rápidos
- Colunas frequentemente buscadas: índices B-Tree
- Buscas de texto: índices GIN com pg_trgm

### Constraints
- NOT NULL: campos obrigatórios
- UNIQUE: valores únicos (ex: numero_bi, numero_processo)
- CHECK: validações de domínio
- FOREIGN KEY: integridade referencial

---

## 7. NOTAÇÃO USADA

```
┌─────────────┐
│  TABELA     │    Rectangle = Entidade
├─────────────┤
│ id (PK)     │    PK = Primary Key (Chave Primária)
│ nome        │    FK = Foreign Key (Chave Estrangeira)
│ email (FK)  │    ── = Relacionamento
│ user_id     │    ◄─ = Referência
└─────────────┘

Cardinalidade:
  1 : 1    Um para um
  1 : N    Um para muitos
  N : 1    Muitos para um
  N : N    Muitos para muitos

Participação:
  ___    Participação total (obrigatória)
  ---    Participação parcial (opcional)
```

---

## 8. EXEMPLOS DE RELACIONAMENTOS

### Exemplo 1: Um Processo → Múltiplos Autos
```
PROCESSOS (1) ──── (N) AUTOS_DECLARACOES
   ▲
   │ Um processo pode gerar
   │ múltiplos autos diferentes
   │
   └─ 1 : N (Um para muitos)
```

### Exemplo 2: Um Queixoso → Múltiplos Processos
```
QUEIXOSOS (N) ──── (1) PROCESSOS
   ▲
   │ Um queixoso pode estar
   │ envolvido em vários processos
   │
   └─ N : 1 (Muitos para um)
```

### Exemplo 3: Um Magistrado → Múltiplas Capas
```
MAGISTRADOS (1) ──── (N) CAPAS_PROCESSOS
     ▲
     │ Um magistrado instrui
     │ múltiplas capas de processo
     │
     └─ 1 : N (Um para muitos)
```

---

## 9. INTEGRIDADE REFERENCIAL

### On Delete
```sql
-- CASCADE: Ao deletar processo, deleta todos autos associados
FOREIGN KEY (processo_id) REFERENCES processos(id) ON DELETE CASCADE

-- SET NULL: Ao deletar, seta FK como NULL
FOREIGN KEY (magistrado_id) REFERENCES magistrados(id) ON DELETE SET NULL

-- RESTRICT: Impede deleção se houver registos dependentes
FOREIGN KEY (tipo_processo_id) REFERENCES tipos_processos(id) ON DELETE RESTRICT
```

### On Update
```sql
-- CASCADE: Ao atualizar PK, atualiza FKs associadas
ON UPDATE CASCADE

-- RESTRICT: Impede atualização se houver dependências
ON UPDATE RESTRICT
```

---

## 10. TABELAS DE SUPORTE

### TIPOS_PROCESSOS
```
Domínio: 'Penal', 'Civil', 'Laboral', 'Comercial'
Função: Categorizar processos
Cardinalidade: 1 : N com PROCESSOS
```

### TIPOS_CRIMES
```
Domínio: Artigos do código penal
Função: Classificar crimes
Cardinalidade: 1 : N com CAPAS_PROCESSOS
```

### TIPOS_ADVOGADOS
```
Domínio: 'Criminal', 'Civil', 'Laboral'
Função: Especialidade do advogado
Cardinalidade: 1 : N com ADVOGADOS
```

### TIPOS_DECLARACOES
```
Domínio: 'Declaração de Queixa', 'Declaração de Comparecer'
Função: Tipo de declaração
Cardinalidade: 1 : N com AUTOS_DECLARACOES
```

### TIPOS_QUALIDADES
```
Domínio: 'Queixoso', 'Arguido', 'Testemunha'
Função: Qualidade da pessoa no processo
Cardinalidade: 1 : N com AVISOS_NOTIFICACOES
```

---

## 11. TABELAS DE DOCUMENTOS (AUTOS)

Todas as tabelas de autos compartilham:
- Relacionamento com PROCESSOS (1 : N)
- Campos de auditoria (created_at, updated_at)
- Referência a USUARIOS (criador)
- `numero_folha` para identificação

Diferenças específicas:
- AUTOS_DECLARACOES: tipo_declaracao_id, advogado_id
- AUTOS_ACARIACOES: arguido_id
- AUTOS_INTERROGATORIOS: arguido_id, defensor_oficioso
- AUTOS_RECONSTITUICOES: tipo_crime_id, meios_utilizados
- AVISOS_NOTIFICACOES: data_comparencia, visto_director

---

## 12. TABELAS DE SUPORTE (PERITOS)

```
PERITOS_RECONSTITUICOES
├─ id (PK)
├─ nome
├─ reconstituicoes_arguidos_id (FK) → AUTOS_RECONSTITUICOES

PERITOS_EXAMES_DIRECTOS
├─ id (PK)
├─ nome
├─ exame_directo_id (FK) → AUTOS_EXAMES_DIRECTOS

PERITOS_EXAMES_DIRECTOS_AVALIACOES
├─ id (PK)
├─ nome
├─ exame_directo_avaliacoes_id (FK) → AUTOS_EXAMES_DIRECTOS_AVALIACOES
```

---

## 13. ESTATÍSTICAS DO MODELO

| Métrica | Valor |
|---------|-------|
| **Total de Entidades** | 34 |
| **Entidades Principais** | 8 |
| **Entidades de Referência** | 5 |
| **Entidades de Documentos** | 18 |
| **Entidades de Suporte** | 3 |
| **Total de Atributos** | ~350 |
| **Total de Relacionamentos** | 40+ |
| **Chaves Primárias** | 34 |
| **Chaves Estrangeiras** | 80+ |
| **Índices** | 32 |
| **Constraints** | 100+ |

---

## 14. PARTICIONAMENTO

### Estratégia
```
PROCESSOS particionada por ANO (RANGE)
├─ processos_2023
├─ processos_2024
├─ processos_2025
└─ processos_2026

AUTOS particionadas por PROCESSO_ID (opcionalmente)
```

### Benefícios
✅ Melhor performance em tabelas grandes
✅ Manutenção mais fácil
✅ Arquivamento de dados antigos
✅ Paralelismo automático

---

## 15. VIEWS MATERIALIZADAS

```
1. mv_processos_resumo
   └─ Processo com contagem de capas, queixosos, arguidos

2. mv_atividades_usuarios
   └─ Atividade por usuário em últimos 30 dias

3. mv_crimes_estatisticas
   └─ Crimes mais comuns com estatísticas

4. mv_notificacoes_pendentes
   └─ Notificações urgentes com status

5. mv_peritos_carregamento
   └─ Distribuição de trabalho dos peritos
```

---

**Modelo ER Completo e Normalizado para Sistema de Gestão Processual** ✅