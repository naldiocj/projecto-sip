CREATE TABLE remessas (
    id BIGSERIAL PRIMARY KEY,
    codigo_rastreio VARCHAR(255) NOT NULL UNIQUE,
    origem VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    data_envio TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    data_recebimento TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(50) NOT NULL,
    responsavel_envio VARCHAR(255) NOT NULL,
    arquivo VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE remessas_documentos (
    remessa_id BIGINT NOT NULL,
    documento_id BIGINT NOT NULL,
    PRIMARY KEY (remessa_id, documento_id),
    CONSTRAINT fk_remessa_documento_remessa FOREIGN KEY (remessa_id) REFERENCES remessas(id),
    CONSTRAINT fk_remessa_documento_documento FOREIGN KEY (documento_id) REFERENCES documentos(id)
);
