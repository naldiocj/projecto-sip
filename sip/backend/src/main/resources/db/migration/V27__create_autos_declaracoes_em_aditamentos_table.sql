CREATE TABLE autos_declaracoes_em_aditamentos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    descricao TEXT,
    tipo_declaracao VARCHAR(255),
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_declaracoes_em_aditamentos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_declaracoes_em_aditamentos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_declaracoes_em_aditamentos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
