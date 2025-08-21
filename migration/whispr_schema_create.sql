-- Whispr Schema Creation Script

DO $$
DECLARE
    schema_name text;
BEGIN
    SELECT current_schema() INTO schema_name;
    EXECUTE format('DROP SCHEMA %I CASCADE', schema_name);
    EXECUTE format('CREATE SCHEMA %I', schema_name);
END $$;


CREATE TABLE user_profile (
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
    CONSTRAINT user_profile_pk PRIMARY KEY (id)
);


CREATE TABLE chat (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    type text NOT NULL,
    title text,
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by uuid,
    deleted_at timestamptz,
    CONSTRAINT chat_pk PRIMARY KEY (id),
    CONSTRAINT chat_type_check CHECK (type IN ('PRIVATE', 'GROUP', 'CHANNEL', 'SYSTEM'))
);


CREATE TABLE chat_member (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    chat_id uuid NOT NULL,
    user_id uuid NOT NULL,
    is_admin boolean NOT NULL DEFAULT false,
    is_muted boolean NOT NULL DEFAULT false,
    joined_at timestamptz NOT NULL DEFAULT now(),
    left_at timestamptz,
    CONSTRAINT chat_member_pk PRIMARY KEY (id),
    CONSTRAINT chat_member_chat_fk FOREIGN KEY (chat_id)
        REFERENCES chat (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX chat_member_unique_idx ON chat_member (chat_id, user_id);


CREATE TABLE message (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    chat_id uuid NOT NULL,
    sender_id uuid NOT NULL,        -- Sender is a participant in the conversation
    type text NOT NULL,
    content text,
    thumbnail_url text,
    media_url text,
    sent_at timestamptz NOT NULL DEFAULT now(),
    last_edited_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz,
    CONSTRAINT message_pk PRIMARY KEY (id),
    CONSTRAINT message_chat_fk FOREIGN KEY (chat_id)
        REFERENCES chat (id) ON DELETE CASCADE,
    CONSTRAINT message_chat_member_fk FOREIGN KEY (sender_id)
        REFERENCES chat_member (id) ON DELETE CASCADE,
    CONSTRAINT message_type_check CHECK (type IN (
        'TEXT', 'IMAGE', 'VIDEO', 'AUDIO', 'FILE', 'STICKER', 'LOCATION', 'CONTACT', 'POLL', 'SYSTEM'
    ))
);

CREATE INDEX message_chat_idx ON message (chat_id);
