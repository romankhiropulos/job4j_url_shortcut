package ru.job4j.urlshortcut.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.controller.PersonController;
import ru.job4j.urlshortcut.dto.LongUrlDTO;
import ru.job4j.urlshortcut.dto.ShortUrlDTO;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.UrlRepository;
import ru.job4j.urlshortcut.util.Encryptor;

import java.util.Optional;

@Service
public class UrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    private final UrlRepository urlRepository;

    private final SiteService siteService;

    private final PersonService personService;

    public UrlService(UrlRepository urlRepository, SiteService siteService, PersonService personService) {
        this.urlRepository = urlRepository;
        this.siteService = siteService;
        this.personService = personService;
    }

    public Optional<ShortUrlDTO> convert(LongUrlDTO longUrl) {

        ShortUrlDTO shortUrlDTO;
        Optional<Url> urlFromDB = this.urlRepository.findByLongUrl(longUrl.getUrl());
        if (urlFromDB.isPresent()) {
            shortUrlDTO = new ShortUrlDTO(urlFromDB.get().getShortUrl());
        } else {
            Url newUrl = new Url();
            Long newUrlId = this.urlRepository.getNextIdSequence();
            newUrl.setId(newUrlId);
            String newShortUrl = Encryptor.toBase62(newUrlId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Person> user = this.personService.findByName((String) authentication.getPrincipal());
            Optional<Site> site = this.siteService.findById(user.get().getSite().getId());
            newUrl.setShortUrl(newShortUrl);
            newUrl.setLongUrl(longUrl.getUrl());
            newUrl.setTotalCalls(0L);
            newUrl.setSite(site.get());
            this.urlRepository.save(newUrl);
            shortUrlDTO = new ShortUrlDTO(newShortUrl);
        }

        return Optional.of(shortUrlDTO);
    }

    public Optional<Url> redirect(String shortCode) {
        Optional<Url> url = urlRepository.findByShortUrl(shortCode);
        if (url.isPresent()) {
            urlRepository.incrementLinkCallsNative(
                    url.get().getId()
            );
            return url;
        }
        return Optional.empty();
    }
}
