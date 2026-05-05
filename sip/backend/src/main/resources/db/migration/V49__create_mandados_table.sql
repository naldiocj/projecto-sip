CREATE TABLE mandados (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    numero_processo VARCHAR(255) NOT NULL,
    nome_requerente VARCHAR(255) NOT NULL,
    nome_executado VARCHAR(255) NOT NULL,
    data_emitido DATE NOT NULL,
    data_validade DATE NOT NULL,
    emitido_por VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    observaciones TEXT,
    arquivo VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
