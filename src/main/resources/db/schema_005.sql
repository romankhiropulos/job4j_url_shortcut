DROP TABLE IF EXISTS url;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS site;
DROP TABLE IF EXISTS role;
DROP SEQUENCE site_id_seq;
DROP SEQUENCE person_id_seq;
DROP SEQUENCE url_id_seq;

CREATE SEQUENCE person_id_seq INCREMENT 1 START WITH 1000000;
CREATE SEQUENCE site_id_seq INCREMENT 1 START WITH 50000;
CREATE SEQUENCE url_id_seq INCREMENT 1 START WITH 10000;

CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE site
(
    id   BIGINT DEFAULT NEXTVAL('site_id_seq') PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE person
(
    id       BIGINT DEFAULT NEXTVAL('person_id_seq') PRIMARY KEY,
    name     VARCHAR,
    password VARCHAR,
    role_id  BIGINT NOT NULL REFERENCES role (id),
    site_id  BIGINT NOT NULL REFERENCES site (id),
    CONSTRAINT name_password_unique UNIQUE (name, password)
);

CREATE TABLE url
(
    id          BIGINT DEFAULT NEXTVAL('url_id_seq') PRIMARY KEY,
    short_url   VARCHAR NOT NULL UNIQUE,
    long_url    VARCHAR NOT NULL UNIQUE,
    total_calls BIGINT DEFAULT 0,
    site_id     BIGINT  NOT NULL REFERENCES person (id)
);
