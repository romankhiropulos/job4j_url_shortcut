package ru.job4j.urlshortcut.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.PersonRepository;
import ru.job4j.urlshortcut.util.Encryptor;

import java.util.*;
import java.util.zip.CRC32;

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
        Optional<Site> siteFromDB = siteService.findByName(site.getName());
        person.setSite(siteFromDB.orElse(site));
        roleService.findByRole("ROLE_USER").ifPresent(person::setRole);
        String name = site.getName() + "_user";
        String newGeneratedPasswordNotEncode = "(next person id + next or exists site id) to base62";
        person.setPassword(encoder.encode(person.getPassword()));
        Person savedPerson = personRepository.save(person);
        savedPerson.setPassword(newGeneratedPasswordNotEncode);
        return savedPerson;
    }

    public static void main(String[] args) {
        System.out.println(Encryptor.toBase62(11157L));
        System.out.println(Encryptor.toBase62(111157L));
        System.out.println(Encryptor.toBase62(1111157L));
        System.out.println(Encryptor.toBase62(11111157L));
        System.out.println(Encryptor.toBase62(111111157L));
        System.out.println(Encryptor.toBase62(1111111157L));
        System.out.println(Encryptor.toBase62(11111111157L));
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
