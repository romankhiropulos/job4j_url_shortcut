package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Long> {

    /**
     * Annotation @Modifying using for resolve problem "could not extract ResultSet"
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query(value = "CALL increment_link_calls_procedure(:id_url_in)", nativeQuery = true)
    void incrementLinkCallsNative(@Param("id_url_in") Long id);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Query(value = "CALL increment_link_calls_procedure(:id_url_in, :answer_inout)", nativeQuery = true)
    String incrementLinkCallsNative(@Param("id_url_in") Long id, @Param("answer_inout") String answer);

    @Query(value = "SELECT NEXTVAL('url_id_seq')", nativeQuery = true)
    Long getNextIdSequence();

    Optional<Url> findByShortUrl(String shortUrl);

    Optional<Url> findByLongUrl(String longUrl);
}
