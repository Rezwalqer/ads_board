-- liquibase formatted sql

-- changeset ruslan:1

CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR NOT NULL,
    password   VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    phone      VARCHAR NOT NULL,
    role       VARCHAR NOT NULL
);

