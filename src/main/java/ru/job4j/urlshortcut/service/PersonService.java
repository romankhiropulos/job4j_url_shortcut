package ru.job4j.urlshortcut.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

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
     *
     * TODO
     *
     * @param site
     * @return
     */
    public Person save(Site site) {
        Person person = new Person();
        Optional<Site> siteFromDB = siteService.findByName(site.getName());
        person.setSite(siteFromDB.orElse(site));
        roleService.findByRole("ROLE_USER").ifPresent(person::setRole);
        String newGeneratedName = "passwordNameTODO";
        String newGeneratedPasswordNotEncode = "passwordTODO";
        person.setPassword(encoder.encode(person.getPassword()));
        Person savedPerson = personRepository.save(person);
        savedPerson.setPassword(newGeneratedPasswordNotEncode);
        return savedPerson;
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
