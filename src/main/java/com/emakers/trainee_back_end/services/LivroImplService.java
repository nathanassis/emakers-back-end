package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.trainee_back_end.exception.LivroNotFoundException;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.repositories.LivroRepository;

@Service
public class LivroImplService implements LivroService {
    @Autowired
    private LivroRepository livroRepository;

    @Override
    public LivroModel validateAndGet(UUID id) {
        return livroRepository.findById(id).orElseThrow(
            () -> new LivroNotFoundException(id)
        );
    }

    @Override
    public LivroModel create(LivroModel livro) {
        return livroRepository.save(livro);
    }

    @Override
    public void delete(LivroModel livro) {
        livroRepository.delete(livro);
    }

    @Override
    public List<LivroModel> getAll() {
        return livroRepository.findAll();
    }
}
