-- Adiciona a coluna direcao_id à tabela processos
-- O tipo BIGINT corresponde ao tipo Long do ID em Java
ALTER TABLE processos ADD COLUMN direcao_id BIGINT;

-- Cria a constraint de chave estrangeira ligando à tabela direcoes
-- Isso garante a integridade referencial entre as tabelas
ALTER TABLE processos 
ADD CONSTRAINT fk_processos_direcao 
FOREIGN KEY (direcao_id) 
REFERENCES direcoes (id);