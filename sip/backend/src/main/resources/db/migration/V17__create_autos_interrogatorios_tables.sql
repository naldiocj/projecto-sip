CREATE TABLE autos_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    descricao TEXT,
    endereco_id BIGINT,
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_aditamentos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_aditamentos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_aditamentos_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_interrogatorios_em_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    defensor_oficioso VARCHAR(255),
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_interrogatorios_em_aditamentos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_interrogatorios_em_aditamentos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_interrogatorios_em_aditamentos_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_interrogatorios_arguidos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    defensor_oficioso VARCHAR(255),
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_interrogatorios_arguidos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_interrogatorios_arguidos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_interrogatorios_arguidos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
