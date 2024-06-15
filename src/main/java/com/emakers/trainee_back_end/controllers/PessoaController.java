package com.emakers.trainee_back_end.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.PessoaDto;
import com.emakers.trainee_back_end.models.LivroModel;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.services.LivroService;
import com.emakers.trainee_back_end.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Autowired
    LivroService livroService;

    @Operation(description = "Cria uma nova pessoa e retorna seus dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a pessoa criada"),
            @ApiResponse(responseCode = "400", description = "Retorna um log do erro")
    })
    @PostMapping
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody @Valid PessoaDto pessoaRequest) {
        var pessoa = new PessoaModel();
        BeanUtils.copyProperties(pessoaRequest, pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.create(pessoa));
    }

    @Operation(description = "Retorna todas as pessoas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um array com todas as pessoas")
    })
    @GetMapping
    public ResponseEntity<List<PessoaModel>> readAllPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.getAll());
    }

    @Operation(description = "Retorna as informações de uma única pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a pessoa com id passado"),
            @ApiResponse(responseCode = "500", description = "Pessoa com id {} não foi encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> readPessoa(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.validateAndGet(id));
    }

    @Operation(description = "Atualiza as informações de uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a pessoa com os dados atualizados"),
            @ApiResponse(responseCode = "500", description = "Pessoa com id {} não foi encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePessoa(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid PessoaDto pessoaRequest) {
        PessoaModel pessoa = pessoaService.validateAndGet(id);
        BeanUtils.copyProperties(pessoaRequest, pessoa);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.create(pessoa));
    }

    @Operation(description = "Deleta uma pessoa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "{nome} deletado(a) com sucesso."),
            @ApiResponse(responseCode = "500", description = "Pessoa com id {} não foi encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoa(@PathVariable(value = "id") UUID id) {
        PessoaModel pessoa = pessoaService.validateAndGet(id);
        pessoaService.delete(pessoa);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("'%s' deletado(a) com sucesso.", pessoa.getNome()));
    }

    // -----
    // Emprestimo

    @Operation(description = "Cria um novo empréstimo entre a pessoa e o livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O livro de id {} foi emprestado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro com id {} não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Pessoa com id {} não foi encontrada")
    })
    @PostMapping("/{idPessoa}/livro/{idLivro}")
    public ResponseEntity<Object> createEmprestimo(@PathVariable(value = "idPessoa") UUID idPessoa,
            @PathVariable(value = "idLivro") UUID idLivro) {
        PessoaModel pessoa = pessoaService.validateAndGet(idPessoa);
        LivroModel livro = livroService.validateAndGet(idLivro);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.addLivro(pessoa, livro));
    }

    @Operation(description = "Deleta um empréstimo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "O livro de id {} foi devolvido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro com id {} não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Pessoa com id {} não foi encontrada")
    })
    @DeleteMapping("/{idPessoa}/livro/{idLivro}")
    public ResponseEntity<Object> deleteEmprestimo(@PathVariable(value = "idPessoa") UUID idPessoa,
            @PathVariable(value = "idLivro") UUID idLivro) {
        PessoaModel pessoa = pessoaService.validateAndGet(idPessoa);
        LivroModel livro = livroService.validateAndGet(idLivro);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.removeLivro(pessoa, livro));
    }

}
