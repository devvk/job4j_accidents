--liquibase formatted sql

--changeset devvk:007_ddl_create_authorities_table
CREATE TABLE authorities
(
    id        SERIAL PRIMARY KEY,
    authority VARCHAR(255) NOT NULL UNIQUE
);

--rollback DROP TABLE authorities;
