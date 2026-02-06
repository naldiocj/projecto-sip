ALTER TABLE IF EXISTS processos ADD COLUMN queixoso_id BIGINT;
ALTER TABLE IF EXISTS processos ADD CONSTRAINT fk_processos_queixoso FOREIGN KEY (queixoso_id) REFERENCES queixosos (id);
