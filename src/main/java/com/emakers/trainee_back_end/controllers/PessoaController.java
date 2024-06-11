package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.PessoaDto;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.repositories.PessoaRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class PessoaController {
    @Autowired
    PessoaRepository pessoaRepository;

    @PostMapping("/pessoa")
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody @Valid PessoaDto pessoaDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoaModel));
    }

    @GetMapping("/pessoa")
    public ResponseEntity<List<PessoaModel>> readAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.findAll());
    }

    @GetMapping("/pessoa/{id}")
    public ResponseEntity<Object> readPessoa(@PathVariable(value = "id") UUID id) {
        Optional<PessoaModel> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoa.get());
    }

    @PutMapping("/pessoa/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid PessoaDto pessoaDto) {
        Optional<PessoaModel> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
        }

        var pessoaModel = pessoa.get();
        BeanUtils.copyProperties(pessoaDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoaModel));
    }

    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable(value = "id") UUID id) {
        Optional<PessoaModel> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrado.");
        }

        pessoaRepository.delete(pessoa.get());
        return ResponseEntity.status(HttpStatus.OK).body(pessoa.get().getNome() + " deletado(a) com sucesso.");
    }

}
