CREATE TABLE IF NOT EXISTS Link
(
    id          BIGINT      GENERATED ALWAYS AS IDENTITY,
    resource    TEXT        NOT NULL,
    updated_at  TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE      (resource)
);

CREATE TABLE IF NOT EXISTS Chat
(
    id          BIGINT      UNIQUE NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS Chat_Link_Association
(
    link_id     BIGINT      REFERENCES Link (id) ON DELETE CASCADE,
    chat_id     BIGINT      REFERENCES Chat (id) ON DELETE CASCADE,
    PRIMARY KEY(link_id, chat_id)
);
