-- Whispr Database Schema (Version 1)

-- ----------------------------------------------------------------
-- Chat Table
-- ----------------------------------------------------------------

CREATE TABLE chat
(
    id         uuid                     NOT NULL DEFAULT gen_random_uuid(),
    type       text                     NOT NULL,
    title      varchar(128),
    created_at timestamp with time zone NOT NULL DEFAULT now(),
    created_by uuid                     NOT NULL,
    deleted_at timestamp with time zone,
    CONSTRAINT chat_pk PRIMARY KEY (id),
    CONSTRAINT chat_type_lkp CHECK (
        type IN ('PRIVATE', 'GROUP', 'CHANNEL', 'SYSTEM')
        )
);


-- ----------------------------------------------------------------
-- Member Table
-- ----------------------------------------------------------------

CREATE TABLE member
(
    id         uuid                     NOT NULL DEFAULT gen_random_uuid(),
    chat_id    uuid                     NOT NULL,
    account_id uuid                     NOT NULL,
    is_admin   boolean                  NOT NULL DEFAULT false,
    is_muted   boolean                  NOT NULL DEFAULT false,
    joined_at  timestamp with time zone NOT NULL DEFAULT now(),
    left_at    timestamp with time zone,
    CONSTRAINT member_pk PRIMARY KEY (id),
    CONSTRAINT member_chat_fk FOREIGN KEY (chat_id) REFERENCES chat (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX member_unique_idx ON member (chat_id, account_id);
CREATE INDEX member_chat_idx ON member (chat_id);


-- ----------------------------------------------------------------
-- Message Table
-- ----------------------------------------------------------------

CREATE TABLE message
(
    id            uuid                     NOT NULL DEFAULT gen_random_uuid(),
    chat_id       uuid                     NOT NULL,
    sender_id     uuid                     NOT NULL,
    content       text                     NOT NULL,
    type          text,
    thumbnail_url varchar(256),
    media_url     varchar(256),
    sent_at       timestamp with time zone NOT NULL DEFAULT now(),
    edited_at     timestamp with time zone,
    deleted_at    timestamp with time zone,
    CONSTRAINT message_pk PRIMARY KEY (id),
    CONSTRAINT message_chat_fk FOREIGN KEY (chat_id) REFERENCES chat (id) ON DELETE CASCADE,
    CONSTRAINT message_sender_fk FOREIGN KEY (sender_id) REFERENCES member (id),
    CONSTRAINT message_type_lkp CHECK (
        type IN ('TEXT', 'IMAGE', 'VIDEO', 'AUDIO', 'FILE', 'STICKER', 'LOCATION', 'CONTACT', 'POLL', 'SYSTEM')
        )
);

CREATE INDEX message_chat_idx ON message (chat_id);


-- ----------------------------------------------------------------
-- Receipt Table
-- ----------------------------------------------------------------

CREATE TABLE receipt
(
    id           uuid NOT NULL DEFAULT gen_random_uuid(),
    message_id   uuid NOT NULL,
    receiver_id  uuid NOT NULL,
    read_at      timestamp with time zone,
    delivered_at timestamp with time zone,
    CONSTRAINT receipt_pk PRIMARY KEY (id),
    CONSTRAINT receipt_message_fk FOREIGN KEY (message_id) REFERENCES message (id) ON DELETE CASCADE,
    CONSTRAINT receipt_receiver_fk FOREIGN KEY (receiver_id) REFERENCES member (id)
);

CREATE UNIQUE INDEX receipt_unique_idx ON receipt (message_id, receiver_id);
