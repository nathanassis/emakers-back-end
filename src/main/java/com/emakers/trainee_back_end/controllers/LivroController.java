package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.LivroDto;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.repositories.LivroRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
public class LivroController {
    @Autowired
    LivroRepository livroRepository;

    @PostMapping("/livro")
    public ResponseEntity<LivroModel> createLivro(@RequestBody @Valid LivroDto livroDto) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroDto, livroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroRepository.save(livroModel));
    }

    @GetMapping("/livro")
    public ResponseEntity<List<LivroModel>> readAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroRepository.findAll());
    }

    @GetMapping("/livro/{id}")
    public ResponseEntity<Object> readLivro(@PathVariable(value = "id") UUID id) {
        Optional<LivroModel> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livro.get());
    }

    @PutMapping("/livro/{id}")
    public ResponseEntity<Object> updateLivro(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid LivroDto livroDto) {
        Optional<LivroModel> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }

        var livroModel = livro.get();
        BeanUtils.copyProperties(livroDto, livroModel);
        return ResponseEntity.status(HttpStatus.OK).body(livroRepository.save(livroModel));
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") UUID id) {
        Optional<LivroModel> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }

        livroRepository.delete(livro.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro " + livro.get().getNome() + " deletado com sucesso.");
    }

}
