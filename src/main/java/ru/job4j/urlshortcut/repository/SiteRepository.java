package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface SiteRepository  extends CrudRepository<Site, Long> {

    Optional<Site> findByName(String name);
}
