CREATE TABLE tipos_crimes (
    id BIGSERIAL PRIMARY KEY,
    artigo_penal VARCHAR(255),
    descricao Text,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_tipos_crimes_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE provincias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE municipios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    provincia_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_municipios_provincia FOREIGN KEY (provincia_id) REFERENCES provincias (id)
);

CREATE TABLE distritos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    municipio_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_distritos_municipio FOREIGN KEY (municipio_id) REFERENCES municipios (id)
);

