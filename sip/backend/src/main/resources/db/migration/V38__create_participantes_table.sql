CREATE TABLE participantes (
    id BIGSERIAL PRIMARY KEY,
    queixoso_id BIGINT,
    advogado_id BIGINT,
    arguido_id BIGINT,
    testemunha_id BIGINT,
    tipo_participante VARCHAR(255),
    processo_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_participantes_queixoso FOREIGN KEY (queixoso_id) REFERENCES queixosos (id),
    CONSTRAINT fk_participantes_advogado FOREIGN KEY (advogado_id) REFERENCES advogados (id),
    CONSTRAINT fk_participantes_arguido FOREIGN KEY (arguido_id) REFERENCES arguidos (id),
    CONSTRAINT fk_participantes_testemunha FOREIGN KEY (testemunha_id) REFERENCES testemunhas (id),
    CONSTRAINT fk_participantes_processo FOREIGN KEY (processo_id) REFERENCES processos (id)
);
