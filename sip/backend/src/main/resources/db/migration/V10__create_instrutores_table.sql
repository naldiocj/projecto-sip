CREATE TABLE instrutores (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255),
    patente_id BIGINT,
    cargo_id BIGINT,
    direcao_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_instrutores_patente FOREIGN KEY (patente_id) REFERENCES patentes (id),
    CONSTRAINT fk_instrutores_cargo FOREIGN KEY (cargo_id) REFERENCES cargos (id),
    CONSTRAINT fk_instrutores_direcao FOREIGN KEY (direcao_id) REFERENCES direcoes (id),
    CONSTRAINT fk_instrutores_user FOREIGN KEY (user_id) REFERENCES users (id)
);
