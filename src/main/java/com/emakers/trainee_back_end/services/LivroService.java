package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.UUID;

import com.emakers.trainee_back_end.models.LivroModel;

public interface LivroService {
    LivroModel validateAndGet(UUID id);

    LivroModel create(LivroModel livro);

    void delete(LivroModel livro);

    List<LivroModel> getAll();
}
