package ru.job4j.urlshortcut.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.urlshortcut.dto.UrlStatisticDTO;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public Site save(Site site) {
        Optional<Site> siteFromDb = siteRepository.findByName(site.getName());
        if (siteFromDb.isEmpty()) {
            return siteRepository.save(site);
        } else {
            Site updatableSite = siteFromDb.get();
            updatableSite.setName(site.getName());
            return siteRepository.save(updatableSite);
        }
    }

    public List<UrlStatisticDTO> getStatisticBySiteId(Long siteId) {
        Optional<Site> site = findById(siteId);
        Set<Url> urls = site.get().getUrls();
        return urls.stream().map(
                url -> new UrlStatisticDTO(url.getLongUrl(), url.getTotalCalls())
        ).collect(Collectors.toList());
    }

    public Optional<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }

    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }
}
