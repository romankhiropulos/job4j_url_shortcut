package ru.job4j.urlshortcut.controller;

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

    public RestartController(RestartEndpoint restartEndpoint, ConfigurableApplicationContext context) {
        this.restartEndpoint = restartEndpoint;
        this.context = context;
    }

    @GetMapping("/actuator")
    public ResponseEntity<RestartAnswer> restartWithActuator() {
        System.setProperty("server.port", String.valueOf(8089));
        System.setProperty("server.servlet.contextPath", "/zontik_url_shortcut");
        restartEndpoint.restart();
        return new ResponseEntity<>(new RestartAnswer("Actuator", "Ok"), HttpStatus.OK);
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

    private static final class RestartAnswer {
        private final String type;
        private final String description;

        public RestartAnswer(String type, String description) {
            this.type = type;
            this.description = description;
        }
    }
}
