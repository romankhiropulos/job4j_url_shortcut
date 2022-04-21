package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByName(String name);

    Optional<Person> findByNameAndPassword(String name, String password);

    @Query(value = "SELECT NEXTVAL('person_id_seq')", nativeQuery = true)
    Long getNextIdSequence();
}
