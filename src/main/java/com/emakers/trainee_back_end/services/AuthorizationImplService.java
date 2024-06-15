package com.emakers.trainee_back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.emakers.trainee_back_end.repositories.PessoaRepository;

public class AuthorizationImplService implements UserDetailsService {
    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return pessoaRepository.findByLogin(login);
    }

}
