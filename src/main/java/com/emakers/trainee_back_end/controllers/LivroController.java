package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.emakers.trainee_back_end.dtos.LivroDto;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.services.LivroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livro")
public class LivroController {
    @Autowired
    LivroService livroService;

    @Operation(description = "Cria um novo livro e retorna seus dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o livro criado"),
        @ApiResponse(responseCode = "400", description = "Retorna um log do erro")
})
    @PostMapping
    public ResponseEntity<LivroModel> createLivro(@RequestBody @Valid LivroDto livroRequest) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRequest, livroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.create(livroModel));
    }

    @Operation(description = "Retorna todos os livros do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna um array com todos os livros")
})
    @GetMapping
    public ResponseEntity<List<LivroModel>> readAllLivros() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.getAll());
    }

    @Operation(description = "Retorna as informações de um único livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o livro com id passado"),
        @ApiResponse(responseCode = "500", description = "Livro com id {} não foi encontrado")
})
    @GetMapping("/{id}")
    public ResponseEntity<Object> readLivro(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.validateAndGet(id));
    }

    @Operation(description = "Atualiza as informações de um livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o livro com os dados atualizados"),
        @ApiResponse(responseCode = "404", description = "Livro com id {} não foi encontrado")
})
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLivro(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid LivroDto livroRequest) {
        LivroModel livro = livroService.validateAndGet(id);
        BeanUtils.copyProperties(livroRequest, livro);
        return ResponseEntity.status(HttpStatus.OK).body(livroService.create(livro));
    }

    @Operation(description = "Deleta um livro do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "{nome} deletado(a) com sucesso."),
        @ApiResponse(responseCode = "404", description = "Livro com id {} não foi encontrado")
})
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") UUID id) {
        LivroModel livro = livroService.validateAndGet(id);
        livroService.delete(livro);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("'%s' deletado com sucesso.", livro.getNome()));
    }

}
