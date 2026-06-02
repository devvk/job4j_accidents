--liquibase formatted sql

--changeset devvk:001_ddl_create_accident_types_table
CREATE TABLE accident_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE accident_types;
