package ru.job4j.urlshortcut.controller;

import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для программируемого рестарта приложения.
 * Использовать стандартный рестарт через actuator можно через POST-запрос ...actuator/restart
 */
@RestController
@RequestMapping("/programmatically_restart")
public class RestartController {

    private static final class RestartAnswer {
        private final String type;
        private final String description;

        public RestartAnswer(String type, String description) {
            this.type = type;
            this.description = description;
        }
    }

    private final RestartEndpoint restartEndpoint;

    public RestartController(RestartEndpoint restartEndpoint) {
        this.restartEndpoint = restartEndpoint;
    }

    @GetMapping("/actuator")
    public ResponseEntity<RestartAnswer> restartWithActuator() {

        Thread restartThread = new Thread(restartEndpoint::restart);
        restartThread.setDaemon(false);
        restartThread.start();

        return new ResponseEntity<>(new RestartAnswer("Actuator", "Ok"), HttpStatus.OK);
    }
}
