CREATE TABLE peritos_exames_directos_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE autos_exames_directos_avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    artigo_examinado VARCHAR(255),
    descricao TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_exames_directos_avaliacoes_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_exames_directos_avaliacoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_exames_directos_avaliacoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_exames_directos_avaliacoes_peritos (
    auto_exame_directo_avaliacao_id BIGINT NOT NULL,
    perito_exame_directo_avaliacao_id BIGINT NOT NULL,
    PRIMARY KEY (auto_exame_directo_avaliacao_id, perito_exame_directo_avaliacao_id),
    CONSTRAINT fk_autos_exames_directos_avaliacoes_peritos_auto FOREIGN KEY (auto_exame_directo_avaliacao_id) REFERENCES autos_exames_directos_avaliacoes (id),
    CONSTRAINT fk_autos_exames_directos_avaliacoes_peritos_perito FOREIGN KEY (perito_exame_directo_avaliacao_id) REFERENCES peritos_exames_directos_avaliacoes (id)
);
