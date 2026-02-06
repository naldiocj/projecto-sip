CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255),
    password VARCHAR(255),
    email_verified BOOLEAN,
    provider VARCHAR(255) NOT NULL,
    provider_id VARCHAR(255),
    active BOOLEAN NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE cargos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    sigla VARCHAR(255),
    descricao VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE direcoes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE,
    sigla VARCHAR(255),
    descricao VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE patentes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) UNIQUE,
    categoria_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_patentes_categoria FOREIGN KEY (categoria_id) REFERENCES categorias (id)
);

CREATE TABLE directores (
    id BIGSERIAL PRIMARY KEY,
    nomeCompleto VARCHAR(255),
    patente_id BIGINT,
    cargo_id BIGINT,
    direcao_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_directores_patente FOREIGN KEY (patente_id) REFERENCES patentes (id),
    CONSTRAINT fk_directores_cargo FOREIGN KEY (cargo_id) REFERENCES cargos (id),
    CONSTRAINT fk_directores_direcao FOREIGN KEY (direcao_id) REFERENCES direcoes (id),
    CONSTRAINT fk_directores_user FOREIGN KEY (user_id) REFERENCES users (id)
);
