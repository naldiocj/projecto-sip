CREATE TABLE autos_acariacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    descricao TEXT,
    endereco_id BIGINT,
    processo_id BIGINT,
    tipo_declaracao VARCHAR(255),
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_acariacoes_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_acariacoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_acariacoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_declaracoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    descricao TEXT,
    endereco_id BIGINT,
    processo_id BIGINT,
    tipo_declaracao VARCHAR(255),
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_declaracoes_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_declaracoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_declaracoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE avisos_notificacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    arguido_id BIGINT,
    endereco_id BIGINT,
    na_qualidade_de VARCHAR(255),
    data_comparencia DATE,
    visto_director BOOLEAN,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_avisos_notificacoes_arguido FOREIGN KEY (arguido_id) REFERENCES arguidos (id),
    CONSTRAINT fk_avisos_notificacoes_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_avisos_notificacoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_avisos_notificacoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);
