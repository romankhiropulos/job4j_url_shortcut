package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.PersonRegistrationDTO;
import ru.job4j.urlshortcut.exception.Operation;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.service.PersonService;
import ru.job4j.urlshortcut.service.SiteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    private final PersonService personService;

    private final SiteService siteService;

    private final BCryptPasswordEncoder encoder;

    private final ObjectMapper objectMapper;

    public PersonController(final PersonService personService,
                            final SiteService siteService,
                            final BCryptPasswordEncoder encoder,
                            final ObjectMapper objectMapper) {

        this.personService = personService;
        this.siteService = siteService;
        this.encoder = encoder;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<PersonRegistrationDTO> signUp(@RequestBody Site site) {
        final PersonRegistrationDTO personRegistrationDTO = new PersonRegistrationDTO();
        Optional<Site> siteFromDB = siteService.findByName(site.getName());
        siteFromDB.ifPresent(site1 ->  personRegistrationDTO.setRegistration(false));
        Person newPerson = personService.save(site);
        personRegistrationDTO.setName(newPerson.getName());
        personRegistrationDTO.setPassword(newPerson.getPassword());
        return new ResponseEntity<>(personRegistrationDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> findAll() {
        var persons = StreamSupport.stream(this.personService.getAll().spliterator(), false)
                .collect(Collectors.toList());
        return ResponseEntity.status(persons.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("Job4jHeader", "job4j")
                .body(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable long id) {
        var person = this.personService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Account is not found. Please, check request."
        ));
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        validatePerson(person);
        return new ResponseEntity<>(
                this.personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        validatePerson(person);
        this.personService.save(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> modify(@Valid @RequestBody Person source)
            throws InvocationTargetException, IllegalAccessException {
        Person destination = match(source);
        validatePerson(destination);
        this.personService.save(destination);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        this.personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        LOGGER.error(e.getLocalizedMessage());
    }

    private void validatePerson(Person person) throws NullPointerException {
        String errMsg = "Username and password must not be empty";
        String name = person.getName();
        String password = person.getPassword();
        Objects.requireNonNull(password, errMsg);
        Objects.requireNonNull(name, errMsg);
        name = name.strip();
        password = password.strip();
        if (Objects.equals(name, "") || Objects.equals(password, "")) {
            throw new NullPointerException(errMsg);
        }
    }

    private Person match(Person source) throws InvocationTargetException, IllegalAccessException {
        var current = personService.findById(source.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method : methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid properties mapping");
                }
                var newValue = getMethod.invoke(source);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        return current;
    }
}
