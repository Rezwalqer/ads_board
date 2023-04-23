-- liquibase formatted sql

-- changeset ruslan:2

CREATE TABLE IF NOT EXISTS ads
(
    id          SERIAL PRIMARY KEY,
    author_id   INTEGER REFERENCES users (id),
    price       INTEGER  NOT NULL,
    title       VARCHAR,
    description TEXT NOT NULL
);

