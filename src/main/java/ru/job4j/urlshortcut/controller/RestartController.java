package ru.job4j.urlshortcut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.model.Person;
import ru.job4j.urlshortcut.repository.CommonRepository;
import ru.job4j.urlshortcut.service.PersonService;

/**
 * Контроллер для программируемого рестарта приложения c помощью двух методов:
 * <p>1) Через RestartEndpoint-actuator</p>
 * <p>2) С помощью перезапуска контекства в новой нити</p>
 * <p>3) Можно использовать стандартный рестарт с помошью actuator,<br>
 *     который создает автоматически <b>POST</b>-запрос по url your_app_url/actuator/restart</p>
 */
@RestController
@RequestMapping("/programmatically_restart")
public class RestartController {

    private ConfigurableApplicationContext context;

    private final RestartEndpoint restartEndpoint;

    private final PersonService personService;

    public RestartController(RestartEndpoint restartEndpoint,
                             ConfigurableApplicationContext context,
                             PersonService personService) {
        this.restartEndpoint = restartEndpoint;
        this.context = context;
        this.personService = personService;
    }

    @GetMapping("/actuator")
    public ResponseEntity<String> restartWithActuator() {
        System.setProperty("server.port", String.valueOf(8089));
        System.setProperty("server.servlet.contextPath", "/zontik_url_shortcut");
        restartEndpoint.restart();
        //return new ResponseEntity<>(new RestartAnswer("Actuator", "Ok"), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/thread")
    public void restartWithNewThread() {

        ApplicationArguments args = context.getBean(ApplicationArguments.class);
        System.setProperty("server.port", String.valueOf(8088));

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(Job4jUrlShortcutApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

    @GetMapping("/pathtoclass")
    public ResponseEntity<String> getPathtoclass() {

        personService.deleteByIdWithThrow(999999L);
        return ResponseEntity.ok("User with id " + 999999L + " has been deleted");
    }

    @Autowired
    private CommonRepository<Person, Long> commonRepository;
    @GetMapping("/pathtokolya")
    public ResponseEntity<String> getPathToKolya() {
        commonRepository.deleteById(1212121212L);
        return ResponseEntity.ok("User with id " + 1212121212L + " has been deleted");
    }

    private static final class RestartAnswer {
        private final String type;
        private final String description;

        public RestartAnswer(String type, String description) {
            this.type = type;
            this.description = description;
        }
    }
}
