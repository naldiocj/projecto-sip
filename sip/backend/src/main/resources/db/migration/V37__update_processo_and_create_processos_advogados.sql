ALTER TABLE processos ADD COLUMN director_id BIGINT;
ALTER TABLE processos ADD CONSTRAINT fk_processos_director FOREIGN KEY (director_id) REFERENCES directores (id);

CREATE TABLE processos_advogados (
    id BIGSERIAL PRIMARY KEY,
    processo_id BIGINT,
    advogado_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_processos_advogados_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_processos_advogados_advogado FOREIGN KEY (advogado_id) REFERENCES advogados (id)
);
