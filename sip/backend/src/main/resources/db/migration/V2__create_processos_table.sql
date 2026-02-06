CREATE TABLE processos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    descricao TEXT,
    numero VARCHAR(255),
    tipo_processo VARCHAR(255),
    ano INTEGER,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_processos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
