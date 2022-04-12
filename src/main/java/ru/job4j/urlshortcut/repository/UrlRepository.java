package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Url;

public interface UrlRepository extends CrudRepository<Url, Long> {
}
