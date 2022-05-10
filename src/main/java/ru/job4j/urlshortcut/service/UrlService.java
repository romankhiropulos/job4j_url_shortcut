package ru.job4j.urlshortcut.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.controller.PersonController;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.util.Optional;

@Service
public class UrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Optional<Url> redirect(String shortCode) {
        Optional<Url> url = urlRepository.findByShortUrl(shortCode);
        if (url.isPresent()) {
            urlRepository.incrementLinkCallsNative(url.get().getId());
            return url;
        }
        return Optional.empty();
    }
}
