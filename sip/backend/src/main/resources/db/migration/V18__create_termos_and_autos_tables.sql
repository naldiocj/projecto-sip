CREATE TABLE termos_entregas (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    visto_director BOOLEAN,
    data_emissao DATE,
    endereco_id BIGINT,
    despacho_magistrado TEXT,
    artigo_apreendido TEXT,
    data_entrega DATE,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_termos_entregas_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_termos_entregas_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_termos_entregas_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE autos_constituicoes_arguidos (
    id BIGSERIAL PRIMARY KEY,
    numero_folha INTEGER,
    data_emissao DATE,
    defensor_oficioso VARCHAR(255),
    endereco_id BIGINT,
    meios_utilizados TEXT,
    descricao TEXT,
    materia_autos TEXT,
    processo_id BIGINT,
    user_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_autos_constituicoes_arguidos_endereco FOREIGN KEY (endereco_id) REFERENCES enderecos (id),
    CONSTRAINT fk_autos_constituicoes_arguidos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_autos_constituicoes_arguidos_user FOREIGN KEY (user_id) REFERENCES users (id)
);
