CREATE TABLE participacoes (
    id BIGSERIAL PRIMARY KEY,
    data_emissao DATE,
    participante VARCHAR(255),
    queixoso_id BIGINT,
    materia_autos TEXT,
    parte_apresentacao TEXT,
    auto_apreensao_texto TEXT,
    auto_apreensao_id BIGINT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_participacoes_queixoso FOREIGN KEY (queixoso_id) REFERENCES queixosos (id),
    CONSTRAINT fk_participacoes_auto_apreensao FOREIGN KEY (auto_apreensao_id) REFERENCES autos_apreensoes (id),
    CONSTRAINT fk_participacoes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_participacoes_user FOREIGN KEY (user_id) REFERENCES users (id)
);
