--liquibase formatted sql

--changeset devvk:010_dml_insert_users
INSERT INTO users (username, password, enabled, authority_id)
SELECT 'root',
       '$2a$10$XBXzEICveElVQtBtZby59O/YbAOEoIPhRSR.ywd6mzSU2yu6kSyMS',
       TRUE,
       id
FROM authorities
WHERE authority = 'ROLE_ADMIN';

--rollback DELETE FROM users WHERE username = 'root';
