CREATE TABLE autos_diligencias (
    id BIGSERIAL PRIMARY KEY,
    introducao TEXT,
    desenvolvimento TEXT,
    conclusao TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_diligencias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_diligencias_user FOREIGN KEY (user_id) REFERENCES users (id)
);
