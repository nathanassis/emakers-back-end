package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.UUID;

import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.models.PessoaModel;

public interface PessoaService {
    PessoaModel validateAndGet(UUID id);

    PessoaModel create(PessoaModel pessoa);

    void delete(PessoaModel pessoa);

    List<PessoaModel> getAll();

    String addLivro(PessoaModel pessoa, LivroModel livro);

    String removeLivro(PessoaModel pessoa, LivroModel livro);
}
