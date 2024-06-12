package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.UUID;

import com.emakers.trainee_back_end.models.PessoaModel;

public interface PessoaService {
    PessoaModel validateAndGet(UUID id);

    PessoaModel create(PessoaModel pessoa);

    void delete(PessoaModel pessoa);

    List<PessoaModel> getAll();
}
