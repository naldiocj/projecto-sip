CREATE TABLE cotas (
    id BIGSERIAL PRIMARY KEY,
    data_emissao DATE,
    materia_autos TEXT,
    termo_juntada TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_cotas_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_cotas_user FOREIGN KEY (user_id) REFERENCES users (id)
);
