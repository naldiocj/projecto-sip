CREATE TABLE cartas_precatorias (
    id BIGSERIAL PRIMARY KEY,
    numero_carta VARCHAR(255),
    data_emissao DATE,
    deprecante VARCHAR(255),
    endereco_id BIGINT,
    descricao TEXT,
    livro_registo_id BIGINT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_cartas_precatorias_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_cartas_precatorias_livro_registo FOREIGN KEY (livro_registo_id) REFERENCES livros_registos (id),
    CONSTRAINT fk_cartas_precatorias_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_cartas_precatorias_user FOREIGN KEY (user_id) REFERENCES users (id)
);
