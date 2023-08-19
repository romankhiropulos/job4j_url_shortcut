package ru.job4j.urlshortcut.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.PersonRepository;
import ru.job4j.urlshortcut.util.Encryptor;

import java.util.*;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final RoleService roleService;

    private final SiteService siteService;

    private final BCryptPasswordEncoder encoder;

    public PersonService(PersonRepository personRepository,
                         RoleService roleService,
                         SiteService siteService,
                         BCryptPasswordEncoder encoder) {

        this.personRepository = personRepository;
        this.roleService = roleService;
        this.siteService = siteService;
        this.encoder = encoder;
    }

    public List<Person> getAll() {
        return (List<Person>) personRepository.findAll();
    }

    public Person save(Person person) {
        roleService.findByRole("ROLE_USER").ifPresent(person::setRole);
        return personRepository.save(person);
    }

    /**
     * Generate name, password for new Person
     * <p>
     * TODO
     *
     * @param site
     * @return
     */
    public Person save(Site site) {
        Person person = new Person();
        Long newId = personRepository.getNextIdSequence();
        person.setId(newId);
        site = siteService.save(site);
        person.setSite(site);
        roleService.findByRole("ROLE_USER").ifPresent(person::setRole);
        person.setName("user_of_".concat(site.getName()));
        String newPasswordBase62 = Encryptor.toBase62((person.getId() + person.getSite().getId()));
        person.setPassword(encoder.encode(newPasswordBase62));
        personRepository.save(person);
        person.setPassword(newPasswordBase62);
        return person;
    }

    public Optional<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    public Optional<Person> findByNameAndPassword(String name, String password) {
        return personRepository.findByNameAndPassword(name, encoder.encode(password));
    }

    public void deleteById(long id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> findById(long id) {
        return personRepository.findById(id);
    }
}
