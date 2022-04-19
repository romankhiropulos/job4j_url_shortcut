DROP TABLE IF EXISTS url;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS site;
DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE person
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role_id  BIGINT      NOT NULL REFERENCES role (id),
    site_id  BIGINT      NOT NULL REFERENCES site (id),
    CONSTRAINT name_password_unique UNIQUE (name, password)
);

CREATE TABLE url
(
    id          BIGSERIAL PRIMARY KEY,
    short_url   VARCHAR NOT NULL UNIQUE,
    long_url    VARCHAR NOT NULL UNIQUE,
    total_calls BIGINT DEFAULT 0,
    site_id     BIGINT  NOT NULL REFERENCES person (id)
);

CREATE TABLE site
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

