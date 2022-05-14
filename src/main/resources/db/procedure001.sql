CREATE OR REPLACE PROCEDURE increment_link_calls_procedure(IN id_url_in BIGINT, INOUT answer_inout TEXT) AS
$$
BEGIN

    answer_inout := (SELECT CURRENT_SETTING('transaction_isolation'));

    UPDATE url
        SET total_calls = total_calls + 1
            WHERE id = id_url_in;

END;
$$ LANGUAGE plpgsql;

