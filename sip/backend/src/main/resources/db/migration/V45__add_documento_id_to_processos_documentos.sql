ALTER TABLE processos_documentos ADD COLUMN documento_id BIGINT;
ALTER TABLE processos_documentos ADD CONSTRAINT fk_processo_documento_documento FOREIGN KEY (documento_id) REFERENCES documentos(id);
