package ru.job4j.urlshortcut.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.UrlStatisticDTO;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.service.PersonService;
import ru.job4j.urlshortcut.service.SiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/site")
public class SiteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class.getSimpleName());

    private final SiteService siteService;

    private final PersonService personService;

    public SiteController(SiteService siteService, PersonService personService) {
        this.siteService = siteService;
        this.personService = personService;
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<UrlStatisticDTO>> statistic(@AuthenticationPrincipal String username) {

        Optional<Person> user = personService.findByName(username);
        List<UrlStatisticDTO> statisticDTOList = new ArrayList<>();
        user.ifPresent(person -> statisticDTOList.addAll(siteService.getStatisticBySiteId(person.getId())));
        return ResponseEntity.status(statisticDTOList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(statisticDTOList);
    }
}
