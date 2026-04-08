CREATE TABLE autos_queixas_arguidos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE autos_queixas_testemunhas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE autos_queixas (
    id BIGSERIAL PRIMARY KEY,
    data_emissao DATE,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_queixas_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_queixas_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_queixas_arguidos_join (
    auto_queixa_id BIGINT NOT NULL,
    arguido_id BIGINT NOT NULL,
    PRIMARY KEY (auto_queixa_id, arguido_id),
    CONSTRAINT fk_autos_queixas_arguidos_join_auto FOREIGN KEY (auto_queixa_id) REFERENCES autos_queixas (id),
    CONSTRAINT fk_autos_queixas_arguidos_join_arguido FOREIGN KEY (arguido_id) REFERENCES autos_queixas_arguidos (id)
);

CREATE TABLE autos_queixas_testemunhas_join (
    auto_queixa_id BIGINT NOT NULL,
    testemunha_id BIGINT NOT NULL,
    PRIMARY KEY (auto_queixa_id, testemunha_id),
    CONSTRAINT fk_autos_queixas_testemunhas_join_auto FOREIGN KEY (auto_queixa_id) REFERENCES autos_queixas (id),
    CONSTRAINT fk_autos_queixas_testemunhas_join_testemunha FOREIGN KEY (testemunha_id) REFERENCES autos_queixas_testemunhas (id)
);
