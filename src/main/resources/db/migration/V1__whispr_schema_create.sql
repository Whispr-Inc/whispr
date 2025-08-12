-- Whispr Schema Creation Script

CREATE TABLE IF NOT EXISTS users (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    username varchar(50) NOT NULL UNIQUE,
    email varchar(100) NOT NULL UNIQUE,
    is_active boolean NOT NULL DEFAULT true,
    first_name varchar(50),
    last_name varchar(50),
    bio text,
    created_at timestamp with time zone NOT NULL DEFAULT now(),
    updated_at timestamp with time zone NOT NULL DEFAULT now(),
    last_seen timestamp with time zone,
    CONSTRAINT users_pk PRIMARY KEY (id)
);
