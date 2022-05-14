package ru.job4j.urlshortcut.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.LongUrlDTO;
import ru.job4j.urlshortcut.dto.ShortUrlDTO;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.UrlService;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<ShortUrlDTO> convert(@RequestBody LongUrlDTO url) {
        Optional<ShortUrlDTO> shortUrlDTO = this.urlService.convert(url);
        return new ResponseEntity<>(shortUrlDTO.get(), HttpStatus.OK);
    }

    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<String> redirect(@PathVariable String shortCode, HttpServletResponse response) {
        Optional<Url> url = urlService.redirect(shortCode);
        return url.map(value -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", value.getLongUrl());
            return new ResponseEntity<String>(headers, HttpStatus.valueOf(302));
        }).orElse(new ResponseEntity<>(shortCode, HttpStatus.CONFLICT));
    }
}
