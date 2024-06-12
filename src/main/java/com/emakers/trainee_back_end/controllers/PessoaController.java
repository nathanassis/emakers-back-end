package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.PessoaDto;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.services.PessoaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody @Valid PessoaDto pessoaRequest) {
        var pessoa = new PessoaModel();
        BeanUtils.copyProperties(pessoaRequest, pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.create(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<PessoaModel>> readAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readPessoa(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.validateAndGet(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid PessoaDto pessoaRequest) {
        PessoaModel pessoa = pessoaService.validateAndGet(id);
        BeanUtils.copyProperties(pessoaRequest, pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.create(pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable(value = "id") UUID id) {
        PessoaModel pessoa = pessoaService.validateAndGet(id);
        pessoaService.delete(pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("'%s' deletado(a) com sucesso.", pessoa.getNome()));
    }

}
