-- liquibase formatted sql

-- changeset ruslan:4

CREATE TABLE IF NOT EXISTS image
(
    id         SERIAL PRIMARY KEY,
    image      BYTEA   NOT NULL,
    file_size  BIGINT NOT NULL,
    media_type TEXT
);

-- changeset ruslan:5
ALTER TABLE ads
    ADD COLUMN image_id INTEGER REFERENCES image (id);

-- changeset ruslan:6
ALTER TABLE users
    ADD COLUMN image_id INTEGER REFERENCES image (id);