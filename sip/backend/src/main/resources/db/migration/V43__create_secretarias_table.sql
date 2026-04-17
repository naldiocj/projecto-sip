CREATE TABLE secretarias (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(255),
    patente_id BIGINT,
    type VARCHAR(50),
    direcao_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_secretaria_patente FOREIGN KEY (patente_id) REFERENCES patentes(id),
    CONSTRAINT fk_secretaria_direcao FOREIGN KEY (direcao_id) REFERENCES direcoes(id),
    CONSTRAINT fk_secretaria_user FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE processos ADD COLUMN secretaria_id BIGINT;
ALTER TABLE processos ADD CONSTRAINT fk_processo_secretaria FOREIGN KEY (secretaria_id) REFERENCES secretarias(id);
