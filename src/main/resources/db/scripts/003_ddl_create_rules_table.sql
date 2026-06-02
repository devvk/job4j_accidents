--liquibase formatted sql

--changeset devvk:003_ddl_create_rules_table
CREATE TABLE rules
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE rules;
