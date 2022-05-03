package ru.job4j.urlshortcut.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.PersonRegistrationDTO;
import ru.job4j.urlshortcut.dto.UrlShortDTO;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

@RestController
@RequestMapping("/url")
public class UrlController {

    @PostMapping("/convert")
    public ResponseEntity<UrlShortDTO> signUp(@RequestBody Site site) {
//        Optional<Url> siteFromDB = urlService.findByName(site.getName());
//        Person newPerson = personService.save(site);
//        PersonRegistrationDTO personRegistrationDTO = objectMapper.convertValue(newPerson, PersonRegistrationDTO.class);
//        if (siteFromDB.isPresent()) {
//            personRegistrationDTO.setRegistration(true);
//        }
        return new ResponseEntity<>(new UrlShortDTO(), HttpStatus.OK);
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> redirect(@RequestBody String shortCode) {

        return new ResponseEntity<>("REDIRECT URL", HttpStatus.FOUND);
    }
}
