package ru.job4j.urlshortcut.service.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.service.SiteService;
import ru.job4j.urlshortcut.service.UrlService;
import ru.job4j.urlshortcut.service.UserDetailsServiceImpl;

@Service
public class TestDiService {

    private final UserDetailsServiceImpl userDetailsService;

    public TestDiService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Autowired
//    private final RoleService roleService;

    @Autowired
    private UrlService urlService;

//    private final PersonService personService;

//    @Autowired
//    public void setPersonService(PersonService personService) {
//        this.personService = personService;
//    }

    private SiteService siteService;

    @Autowired
    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }
}
