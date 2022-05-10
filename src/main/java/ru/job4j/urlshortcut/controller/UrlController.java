package ru.job4j.urlshortcut.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.PersonRegistrationDTO;
import ru.job4j.urlshortcut.dto.UrlShortDTO;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.UrlService;

import java.util.Optional;

@RestController
@RequestMapping("/url")
public class UrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlController.class.getSimpleName());

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlShortDTO> convert(@RequestBody String longUrl) {
//        Optional<Url> siteFromDB = urlService.findByName(site.getName());
//        Person newPerson = personService.save(site);
//        PersonRegistrationDTO personRegistrationDTO = objectMapper.convertValue(newPerson, PersonRegistrationDTO.class);
//        if (siteFromDB.isPresent()) {
//            personRegistrationDTO.setRegistration(true);
//        }
        return new ResponseEntity<>(new UrlShortDTO(), HttpStatus.OK);
    }

    @GetMapping("/redirect/{shortCode}")
    public String redirect(@PathVariable String shortCode) {
        Optional<Url> url = urlService.redirect(shortCode);
        return url.map(value -> "redirect:/".concat(value.getLongUrl())).orElseGet(HttpStatus.NOT_FOUND::toString);
//        return new ResponseEntity<>("REDIRECT URL", HttpStatus.FOUND);
    }
}
