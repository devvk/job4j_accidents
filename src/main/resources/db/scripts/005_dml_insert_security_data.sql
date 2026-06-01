DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE authorities
(
    id        SERIAL PRIMARY KEY,
    authority VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(100) NOT NULL,
    enabled      BOOLEAN DEFAULT true,
    authority_id INT          NOT NULL REFERENCES authorities (id)
);

INSERT INTO authorities (authority)
VALUES ('ROLE_USER');
INSERT INTO authorities (authority)
VALUES ('ROLE_ADMIN');

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root',
        true,
        '$2a$10$XBXzEICveElVQtBtZby59O/YbAOEoIPhRSR.ywd6mzSU2yu6kSyMS',
        (SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));