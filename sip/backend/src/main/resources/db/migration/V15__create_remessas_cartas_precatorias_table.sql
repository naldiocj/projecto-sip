CREATE TABLE remessa_cartas_precatorias (
    id BIGSERIAL PRIMARY KEY,
    numero_oficio VARCHAR(255),
    numero_carta VARCHAR(255),
    assunto VARCHAR(255),
    data_emissao DATE,
    descricao TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_remessa_cartas_precatorias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_remessa_cartas_precatorias_user FOREIGN KEY (user_id) REFERENCES users (id)
);
