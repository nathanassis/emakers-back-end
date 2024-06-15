package com.emakers.trainee_back_end.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.emakers.trainee_back_end.dtos.AuthenticationDto;
import com.emakers.trainee_back_end.dtos.LoginResponseDto;
import com.emakers.trainee_back_end.dtos.PessoaDto;
import com.emakers.trainee_back_end.models.PessoaModel;
import com.emakers.trainee_back_end.repositories.PessoaRepository;
import com.emakers.trainee_back_end.services.TokenService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto data) {
        var usuario = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = authenticationManager.authenticate(usuario);

        var token = tokenService.generateToken((PessoaModel) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid PessoaDto pessoa) {
        if (pessoaRepository.findByLogin(pessoa.login()) != null)
            return ResponseEntity.badRequest().build();

        String senha = new BCryptPasswordEncoder().encode(pessoa.senha());
        PessoaModel novaPessoa = new PessoaModel(
                pessoa.nome(),
                pessoa.cep(),
                pessoa.login(),
                senha,
                pessoa.role());
        
        pessoaRepository.save(novaPessoa);

        return ResponseEntity.status(HttpStatus.OK).body(String.format("'%s' cadastrado com sucesso.", pessoa.nome()));
    }

}
