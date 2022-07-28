package dev.pca.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    @GetMapping(path = "/")
    public ResponseEntity<String> status() {
        LOGGER.debug("operation='status', message='UP'");
        return new ResponseEntity<>("UP", HttpStatus.OK);
    }
}
