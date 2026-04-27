-- Assuming 'pagina' might not exist yet based on your request, but the entity already had it.
-- If it doesn't exist, we add it. If it exists but isn't mandatory, we set it.
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name='documentos' AND column_name='pagina') THEN
        ALTER TABLE documentos ADD COLUMN pagina INTEGER;
    END IF;
END $$;

-- Setting a default value for existing rows to avoid constraint violation if needed
UPDATE documentos SET pagina = 1 WHERE pagina IS NULL;

-- Make it NOT NULL
ALTER TABLE documentos ALTER COLUMN pagina SET NOT NULL;
