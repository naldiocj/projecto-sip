-- ========================================
-- SISTEMA DE GESTÃO PROCESSUAL
-- Schema PostgreSQL Otimizado
-- ========================================

-- Extensões necessárias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pg_trgm;  -- Para buscas fulltext
CREATE EXTENSION IF NOT EXISTS btree_gin; -- Para índices múltiplos



-- ========================================
-- 1. TABELA: USUARIOS
-- ========================================
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    usuario_id BIGINT UNIQUE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

CREATE INDEX idx_usuarios_usuario_id ON usuarios(usuario_id);
CREATE INDEX idx_usuarios_nome ON usuarios USING gin(nome gin_trgm_ops);
CREATE INDEX idx_usuarios_created_at ON usuarios(created_at DESC);

-- ========================================
-- 1.1. TABELA: CATEGORIAS, PATENTES, CARGOS, DIRECTORES
-- ========================================
CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE patentes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    classe_id INT REFERENCES classes(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE direccoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descriao VARCHAR(255) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cargos (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE directores (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    patente_id INT REFERENCES patentes(id) ON DELETE SET NULL,
    cargo_id INT REFERENCES cargos(id) ON DELETE SET NULL,
    direccao_id INT REFERENCES direccoes(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE instrutores (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    patente_id INT REFERENCES patentes(id) ON DELETE SET NULL,
    cargo_id INT REFERENCES cargos(id) ON DELETE SET NULL,
    direccao_id INT REFERENCES direccoes(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- 16. TABELA: TIPOS_DECLARACOES
-- ========================================
CREATE TABLE tipos_declaracoes (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL UNIQUE,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- 3. TABELA: PROCESSOS (Particionada por ano)
-- ========================================
CREATE TABLE processos (
    id BIGSERIAL UNIQUE,
    descricao TEXT,
    numero VARCHAR(50) NOT NULL UNIQUE,
    tipo_processo_id BIGINT NOT NULL REFERENCES tipos_processos(id) ON DELETE RESTRICT,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    usuario_id INT NOT NULL REFERENCES usuarios(usuario_id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ano BIGINT NOT NULL,
    PRIMARY KEY (id, ano)
);

-- Índices na tabela particionada
CREATE INDEX idx_processos_numero ON processos(numero);
CREATE INDEX idx_processos_tipo ON processos(tipo_processo_id);
CREATE INDEX idx_processos_usuario ON processos(usuario_id);
CREATE INDEX idx_processos_created_at ON processos(created_at DESC);

-- ========================================
-- 4. TABELA: TIPOS_CRIMES
-- ========================================
CREATE TABLE tipos_crimes (
    id BIGSERIAL PRIMARY KEY,
    artigo_penal VARCHAR(50),
    descricao TEXT NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_tipos_crimes_descricao ON tipos_crimes USING gin(descricao gin_trgm_ops);
CREATE INDEX idx_tipos_crimes_artigo ON tipos_crimes(artigo_penal);

-- ========================================
-- 5. TABELA: ENDERECOS
-- ========================================
CREATE TABLE enderecos (
    id BIGSERIAL PRIMARY KEY,
    provincia VARCHAR(100),
    municipio VARCHAR(100),
    distrito VARCHAR(100),
    bairro VARCHAR(100),
    rua VARCHAR(255),
    casa VARCHAR(50),
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_enderecos_municipio ON enderecos(municipio);
CREATE INDEX idx_enderecos_bairro ON enderecos(bairro);
CREATE INDEX idx_enderecos_provincia ON enderecos(provincia);

-- ========================================
-- 6. TABELA: MAGISTRADOS
-- ========================================
CREATE TABLE magistrados (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_magistrados_nome ON magistrados USING gin(nome_completo gin_trgm_ops);
CREATE INDEX idx_magistrados_processo ON magistrados(processo_id);

-- ========================================
-- 7. TABELA: TIPOS_ADVOGADOS
-- ========================================
CREATE TABLE tipos_advogados (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL UNIQUE,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- 8. TABELA: ADVOGADOS | ENUM: DEFESA, ACUSACAO
-- ========================================
CREATE TABLE advogados (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    numero_cedula VARCHAR(50) UNIQUE,
    tipo_advogado_id BIGINT REFERENCES tipos_advogados(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_advogados_nome ON advogados USING gin(nome_completo gin_trgm_ops);
CREATE INDEX idx_advogados_cedula ON advogados(numero_cedula);
CREATE INDEX idx_advogados_tipo ON advogados(tipo_advogado_id);
CREATE INDEX idx_advogados_processo ON advogados(processo_id);


-- ========================================
-- 9. TABELA: QUEIXOSOS
-- ========================================
CREATE TABLE queixosos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_pai VARCHAR(255),
    nome_mae VARCHAR(255),
    estado_civil VARCHAR(50),
    idade INT,
    data_nascimento DATE,
    naturalidade VARCHAR(100),
    profissao VARCHAR(100),
    numero_bi VARCHAR(50) UNIQUE,
    data_emissao_bi DATE,
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_queixosos_nome ON queixosos USING gin(nome gin_trgm_ops);
CREATE INDEX idx_queixosos_bi ON queixosos(numero_bi);
CREATE INDEX idx_queixosos_processo ON queixosos(processo_id);
CREATE INDEX idx_queixosos_endereco ON queixosos(endereco_id);
CREATE INDEX idx_queixosos_email ON queixosos(email);

-- ========================================
-- 10. TABELA: TESTEMUNHAS
-- ========================================
CREATE TABLE testemunhas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_pai VARCHAR(255),
    nome_mae VARCHAR(255),
    estado_civil VARCHAR(50),
    idade INT,
    data_nascimento DATE,
    naturalidade VARCHAR(100),
    profissao VARCHAR(100),
    numero_bi VARCHAR(50) UNIQUE,
    data_emissao_bi DATE,
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_testemunhas_nome ON testemunhas USING gin(nome gin_trgm_ops);
CREATE INDEX idx_testemunhas_bi ON testemunhas(numero_bi);
CREATE INDEX idx_testemunhas_processo ON testemunhas(processo_id);
CREATE INDEX idx_testemunhas_endereco ON testemunhas(endereco_id);

-- ========================================
-- 11. TABELA: ARGUIDOS
-- ========================================
CREATE TABLE arguidos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    nome_pai VARCHAR(255),
    nome_mae VARCHAR(255),
    estado_civil VARCHAR(50),
    idade INT,
    data_nascimento DATE,
    naturalidade VARCHAR(100),
    profissao VARCHAR(100),
    numero_bi VARCHAR(50) UNIQUE,
    data_emissao_bi DATE,
    email VARCHAR(255),
    telefone VARCHAR(20),
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_arguidos_nome ON arguidos USING gin(nome gin_trgm_ops);
CREATE INDEX idx_arguidos_bi ON arguidos(numero_bi);
CREATE INDEX idx_arguidos_processo ON arguidos(processo_id);
CREATE INDEX idx_arguidos_endereco ON arguidos(endereco_id);

-- ========================================
-- 12. TABELA: LIVROS_REGISTOS
-- ========================================
CREATE TABLE livros_registos (
    id BIGSERIAL PRIMARY KEY,
    numero_livro VARCHAR(50) NOT NULL UNIQUE,
    numero_folha VARCHAR(50),
    descricao TEXT,
    data_ocorrencia TIMESTAMP,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_livros_numero ON livros_registos(numero_livro);
CREATE INDEX idx_livros_data ON livros_registos(data_ocorrencia);

-- ========================================
-- 13. TABELA: CAPAS_PROCESSOS
-- ========================================
CREATE TABLE capas_processos (
    id BIGSERIAL PRIMARY KEY,
    ano VARCHAR(4),
    numero_expediente VARCHAR(50),
    data_emissao TIMESTAMP,
    endereco VARCHAR(500),
    descricao TEXT,
    livro_registo_id BIGINT REFERENCES livros_registos(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_capas_numero_processo ON capas_processos(numero_processo);
CREATE INDEX idx_capas_magistrado ON capas_processos(magistrado_id);
CREATE INDEX idx_capas_crime ON capas_processos(crime_id);
CREATE INDEX idx_capas_data ON capas_processos(data_ocorrencia);
CREATE INDEX idx_capas_processo ON capas_processos(processo_id);
CREATE INDEX idx_capas_queixoso ON capas_processos(queixoso_id);

-- ========================================
-- 14. TABELA: CARTAS_PRECATORIAS
-- ========================================
CREATE TABLE cartas_precatorias (
    id BIGSERIAL PRIMARY KEY,
    numero_carta VARCHAR(50) UNIQUE,
    deprecante VARCHAR(255),
    data_ocorrencia TIMESTAMP,
    endereco VARCHAR(500),
    descricao TEXT,
    livro_registo_id BIGINT REFERENCES livros_registos(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cartas_numero ON cartas_precatorias(numero_carta);
CREATE INDEX idx_cartas_processo ON cartas_precatorias(numero_processo);
CREATE INDEX idx_cartas_data ON cartas_precatorias(data_ocorrencia);

-- ========================================
-- 15. TABELA: REMESSA_CARTAS_PRECATORIAS
-- ========================================
CREATE TABLE remessa_cartas_precatorias (
    id BIGSERIAL PRIMARY KEY,
    numero_oficio VARCHAR(50),
    assunto VARCHAR(500),
    numero_carta VARCHAR(50),
    numero_processo VARCHAR(50),
    data_emissao DATE,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_remessa_numero_carta ON remessa_cartas_precatorias(numero_carta);
CREATE INDEX idx_remessa_data ON remessa_cartas_precatorias(data_emissao);



-- ========================================
-- 17. TABELA: TIPOS_QUALIDADES
-- ========================================
CREATE TABLE tipos_qualidades (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL UNIQUE,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================================
-- 18. TABELA: AUTOS_DECLARACOES
-- ========================================
CREATE TABLE autos_declaracoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco VARCHAR(500),
    descricao TEXT,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    advogado_id BIGINT REFERENCES advogados(id) ON DELETE SET NULL,
    tipo_declaracao_id BIGINT REFERENCES tipos_declaracoes(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_declaracoes_processo ON autos_declaracoes(processo_id);
CREATE INDEX idx_autos_declaracoes_tipo ON autos_declaracoes(tipo_declaracao_id);
CREATE INDEX idx_autos_declaracoes_data ON autos_declaracoes(data_ocorrencia);

-- ========================================
-- 19. TABELA: AUTOS_ACARIAÇÕES
-- ========================================
CREATE TABLE autos_acariacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    descricao TEXT,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    advogado_id BIGINT REFERENCES advogados(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_acariacoes_processo ON autos_acariacoes(processo_id);
CREATE INDEX idx_autos_acariacoes_data ON autos_acariacoes(data_ocorrencia);

-- ========================================
-- 20. TABELA: AVISOS_NOTIFICACOES
-- ========================================
CREATE TABLE avisos_notificacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    numero_processo INT,
    data_emissao TIMESTAMP,
    endereco_destino_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    na_qualidade_de_id BIGINT REFERENCES tipos_qualidades(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    data_comparencia TIMESTAMP,
    visto_director BOOLEAN DEFAULT false,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_avisos_arguido ON avisos_notificacoes(arguido_id);
CREATE INDEX idx_avisos_data_emissao ON avisos_notificacoes(data_emissao);
CREATE INDEX idx_avisos_data_comparencia ON avisos_notificacoes(data_comparencia);

-- ========================================
-- 21. TABELA: AUTOS_ADITAMENTOS
-- ========================================
CREATE TABLE autos_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    descricao TEXT,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_aditamentos_processo ON autos_aditamentos(processo_id);

-- ========================================
-- 22. TABELA: AUTOS_INTERROGATORIOS_ARGUIDOS
-- ========================================
CREATE TABLE autos_interrogatorios_arguidos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    defensor_oficioso VARCHAR(255),
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_interrogatorios_processo ON autos_interrogatorios_arguidos(processo_id);
CREATE INDEX idx_autos_interrogatorios_arguido ON autos_interrogatorios_arguidos(arguido_id);

-- ========================================
-- 21. TABELA: AUTOS_INTERROGATORIOS_EM_ADITAMENTO
-- ========================================
CREATE TABLE autos_interrogatorios_em_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    defensor_oficioso VARCHAR(255),
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_interrogatorios_em_aditamentos_processo ON autos_interrogatorios_em_aditamentos(processo_id);
CREATE INDEX idx_autos_interrogatorios_em_aditamentos_arguido ON autos_interrogatorios_em_aditamentos(arguido_id);

-- ========================================
-- 23. TABELA: AUTOS_CONSTITUICOES_ARGUIDOS
-- ========================================
CREATE TABLE autos_constituicoes_arguidos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    tipo_crime_id BIGINT REFERENCES tipos_crimes(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    numero_processo INT,
    advogado_id BIGINT REFERENCES advogados(id) ON DELETE SET NULL,
    defensor_oficioso VARCHAR(255),
    meios_utilizados TEXT,
    descricao TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_constituicoes_arguidos_processo ON autos_constituicoes_arguidos(processo_id);
CREATE INDEX idx_autos_constituicoes_arguidos_crime ON autos_constituicoes_arguidos(tipo_crime_id);

-- ========================================
-- 24. TABELA: PERITOS_RECONSTITUICOES
-- ========================================
CREATE TABLE peritos_reconstituicoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    reconstituicoes_arguidos_id BIGINT NOT NULL REFERENCES autos_constituicoes_arguidos(id) ON DELETE CASCADE
);

CREATE INDEX idx_peritos_reconstituicoes_id ON peritos_reconstituicoes(reconstituicoes_arguidos_id);

-- ========================================
-- 25. TABELA: TERMOS_ENTREGAS
-- ========================================
CREATE TABLE termos_entregas (
    id BIGSERIAL PRIMARY KEY,
    visto_director BOOLEAN DEFAULT false,
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    despacho_magistrado TEXT,
    numero_processo VARCHAR(50),
    numero_folha VARCHAR(50),
    artigo_apreendido TEXT,
    data_entrega DATE,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
   usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_termos_entregas_processo ON termos_entregas(processo_id);
CREATE INDEX idx_termos_entregas_data ON termos_entregas(data_entrega);

-- ========================================
-- 26. TABELA: AUTOS_RECONHECIMENTO_FISICO_DIRECTO_OBJETOS
-- ========================================
CREATE TABLE autos_reconhecimento_fisico_directo_objectos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_reconhecimento_fisico_directo_objectos_processo ON autos_reconhecimento_fisico_directo_objectos(processo_id);

-- ========================================
-- 27. TABELA: AUTOS_RECONHECIMENTO_FISICO_DIRECTO_PESSOAS
-- ========================================
CREATE TABLE autos_reconhecimento_fisico_directo_pessoas (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_reconhecimento_fisico_directo_pessoas_processo ON autos_reconhecimento_fisico_directo_pessoas(processo_id);


-- ========================================
-- 28. TABELA: PEDIDOS_COMPARENCIAS
-- ========================================
CREATE TABLE pedidos_comparencias (
    id BIGSERIAL PRIMARY KEY,
    numero_numero BOOLEAN,
    assunto VARCHAR(500),
    numero_processo VARCHAR(50),
    crime_id BIGINT REFERENCES tipos_crimes(id) ON DELETE SET NULL,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    data_comparencia TIMESTAMP,
    na_qualidade_de_id BIGINT REFERENCES tipos_qualidades(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    testemunha_id BIGINT REFERENCES testemunhas(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
   usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pedidos_comparencias_processo ON pedidos_comparencias(processo_id);
CREATE INDEX idx_pedidos_comparencias_data ON pedidos_comparencias(data_comparencia);

-- ========================================
-- 29. TABELA: AUTOS_DEPOIMENTOS_DIRECTOS
-- ========================================
CREATE TABLE autos_depoimentos_directos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_depoimentos_directos_processo ON autos_depoimentos_directos(processo_id);

-- ========================================
-- 29.1. TABELA: AUTOS_DEPOIMENTOS_INDIRECTOS
-- ========================================

CREATE TABLE autos_depoimentos_indirectos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_depoimentos_indirectos_processo ON autos_depoimentos_indirectos(processo_id);

-- ========================================
-- 30. TABELA: AUTOS_EXAMES_DIRECTOS
-- ========================================
CREATE TABLE autos_exames_directos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    artigo_examinado VARCHAR(500),
    descricao TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_exames_directos_processo ON autos_exames_directos(processo_id);

-- ========================================
-- 31. TABELA: PERITOS_EXAMES_DIRECTOS
-- ========================================
CREATE TABLE peritos_exames_directos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    exame_directo_id BIGINT NOT NULL REFERENCES autos_exames_directos(id) ON DELETE CASCADE
);

CREATE INDEX idx_peritos_exames_id ON peritos_exames_directos(exame_directo_id);

-- ========================================
-- 32. TABELA: AUTOS_EXAMES_DIRECTOS_AVALIACOES
-- ========================================
CREATE TABLE autos_exames_directos_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    artigo_examinado VARCHAR(500),
    descricao TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_exames_directos_avaliacoes_processo ON autos_exames_directos_avaliacoes(processo_id);

-- ========================================
-- 33. TABELA: PERITOS_EXAMES_AVALIACOES
-- ========================================
CREATE TABLE peritos_exames_directos_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    exame_directo_avaliacoes_id BIGINT NOT NULL REFERENCES autos_exames_directos_avaliacoes(id) ON DELETE CASCADE
);

-- ========================================
-- 34. TABELA: AUTOS_RECONSTITUICOES
-- ========================================
CREATE TABLE autos_reconstituicoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    arguido_id BIGINT REFERENCES arguidos(id) ON DELETE SET NULL,
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    meios_utilizados TEXT,
    descricao TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_reconstituicoes_processo ON autos_reconstituicoes(processo_id);
CREATE INDEX idx_autos_reconstituicoes_data ON autos_reconstituicoes(data_ocorrencia);

-- ========================================
-- 29. TABELA: AUTOS_RECONHECIMENTO_DESCRICOES_PESSOAS
-- ========================================
CREATE TABLE autos_reconhecimento_descricoes_pessoas (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_reconhecimento_descricoes_pessoas_processo ON autos_reconhecimento_descricoes_pessoas(processo_id);

-- ========================================
-- 30. TABELA: AUTOS_RECONHECIMENTO_DESCRICOES_OBJECTOS
-- ========================================
CREATE TABLE autos_reconhecimento_descricoes_objectos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_reconhecimento_descricoes_objectos_processo ON autos_reconhecimento_descricoes_objectos(processo_id);

-- ========================================
-- 31. TABELA: AUTOS_APREENSOES
-- ========================================
CREATE TABLE autos_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    na_qualidade_de_id BIGINT REFERENCES tipos_qualidades(id) ON DELETE SET NULL,
    natureza_caracteristicas VARCHAR(500),
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_apreensoes_processo ON autos_apreensoes(processo_id);

-- ========================================
-- 31.1. TABELA: PARTICIPANTES_APREENSES
-- ========================================
CREATE TABLE participantes_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    auto_apreensao_id BIGINT NOT NULL REFERENCES autos_apreensoes(id) ON DELETE CASCADE
);
-- ========================================
-- 31.2. TABELA: OBJECTOS_APREENSES
-- ========================================
CREATE TABLE objectos_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    auto_apreensao_id BIGINT NOT NULL REFERENCES autos_apreensoes(id) ON DELETE CASCADE
);

-- ========================================
-- 32. TABELA: AUTOS_CORPO_DELITO_INDIRECTO
-- ========================================
CREATE TABLE autos_corpo_delito_indirecto (
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    endereco_id BIGINT REFERENCES enderecos(id) ON DELETE SET NULL,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    numero_processo VARCHAR(50),
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_corpo_delito_indirecto_processo ON autos_corpo_delito_indirecto(processo_id);

-- ========================================
-- 32.1. TABELA: TESTEMUNHAS_CORPO_DELITO_INDIRECTO
-- ========================================
CREATE TABLE testemunhas_corpo_delito_indirecto (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    auto_corpo_delito_indirecto_id BIGINT NOT NULL REFERENCES autos_corpo_delito_indirecto(id) ON DELETE CASCADE
);

-- ========================================
-- 33. TABELA: AUTOS_DECLARACOES_EM_ADITAMENTO
-- ========================================
CREATE TABLE autos_declaracoes_em_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha VARCHAR(50),
    data_ocorrencia TIMESTAMP,
    endereco VARCHAR(500),
    descricao TEXT,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    advogado_id BIGINT REFERENCES advogados(id) ON DELETE SET NULL,
    tipo_declaracao_id BIGINT REFERENCES tipos_declaracoes(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_declaracoes_em_aditamentos_processo ON autos_declaracoes_em_aditamentos(processo_id);
CREATE INDEX idx_autos_declaracoes_em_aditamentos_tipo ON autos_declaracoes_em_aditamentos(tipo_declaracao_id);
CREATE INDEX idx_autos_declaracoes_em_aditamentos_data ON autos_declaracoes_em_aditamentos(data_ocorrencia);

-- ========================================
-- 33. TABELA: AUTOS_DILIGENCIAS
-- ========================================
CREATE TABLE autos_diligencias (
    id BIGSERIAL PRIMARY KEY,
    introducao TEXT,
    desenvolvimento TEXT,
    conclusao TEXT,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_diligencias_processo ON autos_diligencias(processo_id);

-- ========================================
-- 34. TABELA: AUTOS_QUEIXAS
-- ========================================
CREATE TABLE autos_queixas (    
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    director_id BIGINT REFERENCES directores(id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_autos_queixas_processo ON autos_queixas(processo_id);

-- ========================================
-- 34.1. TABELA: ARGUIDOS_AUTOS_QUEIXAS
-- ========================================
CREATE TABLE arguidos_autos_queixas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    auto_queixa_id BIGINT NOT NULL REFERENCES autos_queixas(id) ON DELETE CASCADE
);
-- ========================================
-- 34.2. TABELA: TESTEMUNHAS_AUTOS_QUEIXAS
-- ========================================
CREATE TABLE testemunhas_autos_queixas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    auto_queixa_id BIGINT NOT NULL REFERENCES autos_queixas(id) ON DELETE CASCADE
);

-- ========================================
-- 34. TABELA: EDITAIS
-- ========================================
CREATE TABLE editais (    
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    materia_autos TEXT,
    direccao_id BIGINT REFERENCES direccoes(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_editais_processo ON editais(processo_id);

-- ========================================
-- 35. TABELA: INFORMAÇÕES
-- ========================================
CREATE TABLE informacoes (    
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    instructor_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    materia_autos TEXT,
    direccao_id BIGINT REFERENCES direccoes(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_informacoes_processo ON informacoes(processo_id);

-- ========================================
-- 35. TABELA: COTAS
-- ========================================
CREATE TABLE cotas (    
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    materia_autos TEXT,
    termo_juntada TEXT,
    direccao_id BIGINT REFERENCES direccoes(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_cotas_processo ON cotas(processo_id);

-- ========================================
-- 35. TABELA: PARTICIPAÇÕES
-- ========================================
CREATE TABLE participacoes (    
    id BIGSERIAL PRIMARY KEY,
    data_ocorrencia TIMESTAMP,
    participante VARCHAR(255),
    queixoso_id BIGINT REFERENCES queixosos(id) ON DELETE SET NULL,
    materia_autos TEXT,
    parte_apresentacao TEXT,
    auto_apreensao TEXT,
    direccao_id BIGINT REFERENCES direccoes(id) ON DELETE SET NULL,
    processo_id BIGINT REFERENCES processos (id) ON DELETE SET NULL,
    usuario_id BIGINT REFERENCES usuarios(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_participacoes_processo ON participacoes(processo_id);


-- ========================================
-- FUNÇÃO: Atualizar timestamp automaticamente
-- ========================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Criar triggers para todos as tabelas principais
CREATE TRIGGER trigger_usuarios_updated_at BEFORE UPDATE ON usuarios
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_processos_updated_at BEFORE UPDATE ON processos
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_queixosos_updated_at BEFORE UPDATE ON queixosos
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_arguidos_updated_at BEFORE UPDATE ON arguidos
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_testemunhas_updated_at BEFORE UPDATE ON testemunhas
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_advogados_updated_at BEFORE UPDATE ON advogados
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Análise automática
ANALYZE;