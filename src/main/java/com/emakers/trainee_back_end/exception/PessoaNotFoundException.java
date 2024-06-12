package com.emakers.trainee_back_end.exception;

import java.util.UUID;

public class PessoaNotFoundException extends RuntimeException {
    public PessoaNotFoundException(UUID id) {
        super(String.format("Pessoa com id '%s' não foi encontrada.", id));
    }
}
