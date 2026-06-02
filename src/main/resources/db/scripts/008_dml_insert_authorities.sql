--liquibase formatted sql

--changeset devvk:008_dml_insert_authorities
INSERT INTO authorities (authority)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_MODERATOR');

--rollback DELETE FROM authorities WHERE authority IN ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR');
