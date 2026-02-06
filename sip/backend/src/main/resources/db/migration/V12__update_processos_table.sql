ALTER TABLE processos ADD COLUMN magistrado_id BIGINT;
ALTER TABLE processos ADD COLUMN instrutor_id BIGINT;

ALTER TABLE processos ADD CONSTRAINT fk_processos_magistrado FOREIGN KEY (magistrado_id) REFERENCES magistrados (id);
ALTER TABLE processos ADD CONSTRAINT fk_processos_instrutor FOREIGN KEY (instrutor_id) REFERENCES instrutores (id);

CREATE TABLE processo_crimes (
    processo_id BIGINT NOT NULL,
    crime_id BIGINT NOT NULL,
    PRIMARY KEY (processo_id, crime_id),
    CONSTRAINT fk_processo_crimes_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_processo_crimes_crime FOREIGN KEY (crime_id) REFERENCES tipos_crimes (id)
);

CREATE TABLE processo_arguidos (
    processo_id BIGINT NOT NULL,
    arguido_id BIGINT NOT NULL,
    PRIMARY KEY (processo_id, arguido_id),
    CONSTRAINT fk_processo_arguidos_processo FOREIGN KEY (processo_id) REFERENCES processos (id),
    CONSTRAINT fk_processo_arguidos_arguido FOREIGN KEY (arguido_id) REFERENCES arguidos (id)
);
