--liquibase formatted sql

--changeset devvk:006_ddl_create_accident_rules_table
CREATE TABLE accident_rules
(
    accident_id INT NOT NULL REFERENCES accidents (id),
    rule_id     INT NOT NULL REFERENCES rules (id),
    PRIMARY KEY (accident_id, rule_id)
);

--rollback DROP TABLE accident_rules;
