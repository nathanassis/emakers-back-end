package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.LivroDto;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.services.LivroService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livro")
public class LivroController {
    @Autowired
    LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroModel> createLivro(@RequestBody @Valid LivroDto livroRequest) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRequest, livroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.create(livroModel));
    }

    @GetMapping
    public ResponseEntity<List<LivroModel>> readAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readLivro(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.validateAndGet(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLivro(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid LivroDto livroRequest) {
        LivroModel livro = livroService.validateAndGet(id);
        BeanUtils.copyProperties(livroRequest, livro);
        return ResponseEntity.status(HttpStatus.OK).body(livroService.create(livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") UUID id) {
        LivroModel livro = livroService.validateAndGet(id);
        livroService.delete(livro);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("'%s' deletado com sucesso.", livro.getNome()));
    }

}
