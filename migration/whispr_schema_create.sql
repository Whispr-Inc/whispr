-- Whispr Schema Creation Script

DO $$
DECLARE
    schema_name text;
BEGIN
    SELECT current_schema() INTO schema_name;
    EXECUTE format('DROP SCHEMA %I CASCADE', schema_name);
    EXECUTE format('CREATE SCHEMA %I', schema_name);
END $$;


CREATE TABLE IF NOT EXISTS profiles (
    id uuid NOT NULL,
    username text NOT NULL UNIQUE,
    email text NOT NULL UNIQUE,
    is_active boolean NOT NULL DEFAULT true,
    first_name text,
    last_name text,
    bio text,
    joined_at timestamptz NOT NULL DEFAULT now(),
    last_synced_at timestamptz NOT NULL DEFAULT now(),
    last_seen timestamptz,
    CONSTRAINT users_pk PRIMARY KEY (id)
);
