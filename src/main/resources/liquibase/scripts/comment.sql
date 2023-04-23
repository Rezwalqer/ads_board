-- liquibase formatted sql

-- changeset ruslan:3

CREATE TABLE IF NOT EXISTS comment
(
    id         SERIAL PRIMARY KEY,
    author_id  INTEGER REFERENCES users (id),
    created_at TIMESTAMP,
    text       TEXT,
    ads_id     INTEGER REFERENCES ads (id)
);
