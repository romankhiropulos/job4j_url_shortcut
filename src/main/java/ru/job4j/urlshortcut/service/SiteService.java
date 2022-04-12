package ru.job4j.urlshortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

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

    public Optional<Site> findByName(String name) {
        return siteRepository.findByName(name);
    }
}
