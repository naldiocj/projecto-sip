CREATE TABLE despachos (
    id BIGSERIAL PRIMARY KEY,
    numero_processo VARCHAR(255),
    decisao TEXT,
    data_despacho TIMESTAMP WITHOUT TIME ZONE,
    autoridade_responsavel VARCHAR(255),
    observacoes TEXT,
    processo_id BIGINT,
    is_finalizado BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_despacho_processo FOREIGN KEY (processo_id) REFERENCES processos(id)
);
