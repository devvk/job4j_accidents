--liquibase formatted sql

--changeset devvk:009_ddl_create_users_table
CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    enabled      BOOLEAN NOT NULL DEFAULT TRUE,
    authority_id INT NOT NULL REFERENCES authorities(id)
);

--rollback DROP TABLE users;
