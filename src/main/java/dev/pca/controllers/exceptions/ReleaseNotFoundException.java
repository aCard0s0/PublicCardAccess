package dev.pca.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReleaseNotFoundException extends RuntimeException {

    public ReleaseNotFoundException(String message) {
        super(message);
    }
}
