CREATE TABLE autos_corpo_delitos_indirectos (
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
    CONSTRAINT fk_autos_corpo_delitos_indirectos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_corpo_delitos_indirectos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_corpo_delitos_indirectos_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_corpo_delitos_indirectos_testemunhas (
    auto_corpo_delito_indirecto_id BIGINT NOT NULL,
    testemunha_id BIGINT NOT NULL,
    PRIMARY KEY (auto_corpo_delito_indirecto_id, testemunha_id),
    CONSTRAINT fk_autos_corpo_delitos_indirectos_testemunhas_auto FOREIGN KEY (auto_corpo_delito_indirecto_id) REFERENCES autos_corpo_delitos_indirectos (id),
    CONSTRAINT fk_autos_corpo_delitos_indirectos_testemunhas_testemunha FOREIGN KEY (testemunha_id) REFERENCES testemunhas (id)
);
