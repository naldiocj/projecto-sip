CREATE TABLE magistrados (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255),
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_magistrados_user FOREIGN KEY (user_id) REFERENCES users (id)
);
