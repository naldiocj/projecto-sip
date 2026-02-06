CREATE TABLE advogados (
    id BIGSERIAL PRIMARY KEY,
    nomeCompleto VARCHAR(255),
    numero_cedula VARCHAR(255) UNIQUE,
    tipo_advogado VARCHAR(255),
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_advogados_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_advogados_user FOREIGN KEY (user_id) REFERENCES users (id)
);
