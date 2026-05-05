ALTER TABLE mandados ADD COLUMN user_id BIGINT;
ALTER TABLE mandados ADD CONSTRAINT fk_mandado_user FOREIGN KEY (user_id) REFERENCES users(id);
