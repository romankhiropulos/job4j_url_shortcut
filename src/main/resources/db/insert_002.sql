DELETE FROM role;
DELETE FROM url;
DELETE FROM site;

INSERT INTO role (role)
    VALUES ('ROLE_USER');
INSERT INTO role (role)
    VALUES ('ROLE_ADMIN');

INSERT INTO site (id, name) VALUES (50000, 'job4j.ru');

INSERT INTO url (short_url, long_url, site_id) VALUES ('shortUrl', 'job4j.ru', 50000);