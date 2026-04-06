CREATE TABLE diligencias (
    id BIGSERIAL PRIMARY KEY,
    ordem INTEGER,
    etapa VARCHAR(255),
    titulo VARCHAR(255),
    descricao TEXT,
    prazo DATE,
    estado VARCHAR(255),
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_diligencias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_diligencias_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE processos_diligencias (
    processo_id BIGINT NOT NULL,
    diligencia_id BIGINT NOT NULL,
    PRIMARY KEY (processo_id, diligencia_id),
    CONSTRAINT fk_processos_diligencias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_processos_diligencias_diligencia FOREIGN KEY (diligencia_id) REFERENCES diligencias (id)
);
