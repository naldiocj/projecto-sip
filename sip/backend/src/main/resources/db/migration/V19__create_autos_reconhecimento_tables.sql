CREATE TABLE autos_reconhecimento_fisico_directo_pessoas (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_pessoas_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_pessoas_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_pessoas_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_reconhecimento_fisico_directo_objectos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    endereco_id BIGINT,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_objectos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_objectos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_reconhecimento_fisico_directo_objectos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
