package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.trainee_back_end.exception.PessoaNotFoundException;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.repositories.PessoaRepository;

@Service
public class PessoaImplService implements PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public PessoaModel validateAndGet(UUID id) {
        return pessoaRepository.findById(id).orElseThrow(
            () -> new PessoaNotFoundException(id)
        );
    }

    @Override
    public PessoaModel create(PessoaModel pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public void delete(PessoaModel pessoa) {
        pessoaRepository.delete(pessoa);
    }

    @Override
    public List<PessoaModel> getAll() {
        return pessoaRepository.findAll();
    }
}
