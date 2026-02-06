CREATE TABLE pedidos_comparencias (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_comparencia DATE,
    na_qualidade_de VARCHAR(255),
    assunto VARCHAR(255),
    endereco_id BIGINT,
    arguido_id BIGINT,
    testemunha_id BIGINT,
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_pedidos_comparencias_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_pedidos_comparencias_arguido FOREIGN KEY (arguido_id) REFERENCES arguidos (id),
    CONSTRAINT fk_pedidos_comparencias_testemunha FOREIGN KEY (testemunha_id) REFERENCES testemunhas (id),
    CONSTRAINT fk_pedidos_comparencias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_pedidos_comparencias_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_depoimentos_directos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    assunto VARCHAR(255),
    endereco_id BIGINT,
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_depoimentos_directos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_depoimentos_directos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_depoimentos_directos_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_depoimentos_indirectos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    assunto VARCHAR(255),
    endereco_id BIGINT,
    processo_id BIGINT,
    materia_autos TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_depoimentos_indirectos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_depoimentos_indirectos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_depoimentos_indirectos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
