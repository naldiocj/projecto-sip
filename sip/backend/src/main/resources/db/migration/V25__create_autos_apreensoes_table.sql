CREATE TABLE participantes_autos_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE objectos_autos_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE autos_apreensoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    na_qualidade_de VARCHAR(255),
    natureza_caracteristicas TEXT,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_apreensoes_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_apreensoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_apreensoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_aprensoes_participantes (
    auto_apreensao_id BIGINT NOT NULL,
    participante_id BIGINT NOT NULL,
    PRIMARY KEY (auto_apreensao_id, participante_id),
    CONSTRAINT fk_autos_aprensoes_participantes_auto FOREIGN KEY (auto_apreensao_id) REFERENCES autos_apreensoes (id),
    CONSTRAINT fk_autos_aprensoes_participantes_participante FOREIGN KEY (participante_id) REFERENCES participantes_autos_apreensoes (id)
);

CREATE TABLE autos_aprensoes_objectos (
    auto_apreensao_id BIGINT NOT NULL,
    objecto_id BIGINT NOT NULL,
    PRIMARY KEY (auto_apreensao_id, objecto_id),
    CONSTRAINT fk_autos_aprensoes_objectos_auto FOREIGN KEY (auto_apreensao_id) REFERENCES autos_apreensoes (id),
    CONSTRAINT fk_autos_aprensoes_objectos_objecto FOREIGN KEY (objecto_id) REFERENCES objectos_autos_apreensoes (id)
);
