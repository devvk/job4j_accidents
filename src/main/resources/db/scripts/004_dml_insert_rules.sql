--liquibase formatted sql

--changeset devvk:004_dml_insert_rules
INSERT INTO rules (name)
VALUES ('Статья 1'),
       ('Статья 2'),
       ('Статья 3');

--rollback DELETE FROM rules;