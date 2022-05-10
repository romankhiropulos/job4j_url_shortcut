package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

/**
 * Annotation @Modifying using for resolve problem "could not extract ResultSet"
 */
public interface UrlRepository extends CrudRepository<Url, Long> {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Query(value = "CALL increment_link_calls_procedure(:id_url_in)", nativeQuery = true)
    void incrementLinkCallsNative(@Param("id_url_in") Long id);

    Optional<Url> findByShortUrl(String shortUrl);
}
