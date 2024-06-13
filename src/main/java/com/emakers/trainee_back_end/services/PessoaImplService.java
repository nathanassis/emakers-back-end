package com.emakers.trainee_back_end.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emakers.trainee_back_end.exception.PessoaNotFoundException;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.repositories.PessoaRepository;

@Service
public class PessoaImplService implements PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public PessoaModel validateAndGet(UUID id) {
        return pessoaRepository.findById(id).orElseThrow(
                () -> new PessoaNotFoundException(id));
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

    @Override
    public String addLivro(PessoaModel pessoa, LivroModel livro) {
        Set<LivroModel> livrosEmprestados = pessoa.getLivros();
        for (LivroModel livroEmprestado : livrosEmprestados) {
            if (livroEmprestado.equals(livro)) {
                return String.format("O livro de id '%s' já foi emprestado para essa pessoa.", livro.getIdLivro());
            }
        }

        pessoa.addLivro(livro);
        pessoaRepository.save(pessoa);
        return String.format("O livro de id '%s' foi emprestado com sucesso.", livro.getIdLivro());
    }

    @Override
    public String removeLivro(PessoaModel pessoa, LivroModel livro) {
        Set<LivroModel> livrosEmprestados = pessoa.getLivros();
        for (LivroModel livroEmprestado : livrosEmprestados) {
            if (livroEmprestado.equals(livro)) {
                pessoa.removeLivro(livro);
                pessoaRepository.save(pessoa);
                return String.format("O livro de id '%s' foi devolvido com sucesso.", livro.getIdLivro());
            }
        }

        return String.format("O livro de id '%s' não foi emprestado para essa pessoa.", livro.getIdLivro());
    }
}
