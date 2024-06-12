package com.emakers.trainee_back_end.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNotFoundException extends RuntimeException {
    public LivroNotFoundException(UUID id) {
        super(String.format("Livro com id '%s' não foi encontrado.", id));
    }
}
