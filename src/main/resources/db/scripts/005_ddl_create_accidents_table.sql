--liquibase formatted sql

--changeset devvk:005_ddl_create_accidents_table
CREATE TABLE accidents
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    text    TEXT,
    address VARCHAR(255),
    type_id INT          NOT NULL REFERENCES accident_types (id)
);

--rollback DROP TABLE accidents;
