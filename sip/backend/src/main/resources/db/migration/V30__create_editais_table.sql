CREATE TABLE editais (
    id BIGSERIAL PRIMARY KEY,
    data_emissao DATE,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_editais_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_editais_user FOREIGN KEY (user_id) REFERENCES users (id)
);
