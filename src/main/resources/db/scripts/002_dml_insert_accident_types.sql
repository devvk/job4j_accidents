--liquibase formatted sql

--changeset devvk:002_dml_insert_accident_types
INSERT INTO accident_types (name)
VALUES ('Две машины'),
       ('Машина и человек'),
       ('Машина и велосипед');

--rollback DELETE FROM accident_types;
