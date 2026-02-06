CREATE TABLE livros_registos (
    id BIGSERIAL PRIMARY KEY,
    numero_livro VARCHAR(255),
    numero_folha INTEGER,
    data_ocorrencia DATE,
    descricao TEXT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_livros_registos_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE capas_processos (
    id BIGSERIAL PRIMARY KEY,
    ano INTEGER,
    numero_expediente VARCHAR(255),
    data_emissao DATE,
    endereco_id BIGINT,
    livro_registo_id BIGINT,
    descricao TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_capas_processos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_capas_processos_livro_registo FOREIGN KEY (livro_registo_id) REFERENCES livros_registos (id),
    CONSTRAINT fk_capas_processos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_capas_processos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
