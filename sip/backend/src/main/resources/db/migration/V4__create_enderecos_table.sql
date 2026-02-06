CREATE TABLE enderecos (
    id BIGSERIAL PRIMARY KEY,
    provincia_id BIGINT,
    municipio_id BIGINT,
    distrito_id BIGINT,
    bairro VARCHAR(255),
    rua VARCHAR(255),
    casa VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_enderecos_provincia FOREIGN KEY (provincia_id) REFERENCES provincias (id),
    CONSTRAINT fk_enderecos_municipio FOREIGN KEY (municipio_id) REFERENCES municipios (id),
    CONSTRAINT fk_enderecos_distrito FOREIGN KEY (distrito_id) REFERENCES distritos (id)
);
