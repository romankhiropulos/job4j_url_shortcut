CREATE OR REPLACE PROCEDURE increment_link_calls(id_url BIGINT) AS
$$
DECLARE
--    titles   TEXT DEFAULT '';
--    rec_film RECORD;
--     cur_films CURSOR (p_year INTEGER)
--         FOR SELECT title, release_year
--             FROM film
--             WHERE release_year = p_year;
BEGIN

    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE READ WRITE;

    UPDATE url
        SET total_calls = (SELECT total_calls FROM url WHERE id = id_url) + 1
            WHERE id = id_url;

END;
$$ LANGUAGE plpgsql;


