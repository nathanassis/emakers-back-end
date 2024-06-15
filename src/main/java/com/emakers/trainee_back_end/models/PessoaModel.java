package com.emakers.trainee_back_end.models;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Pessoa")
public class PessoaModel implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID idPessoa;

    @Column(nullable = false, columnDefinition = "VARCHAR(80)")
    private String nome;

    @Column(nullable = false, columnDefinition = "CHAR(9)")
    private String cep;

    @Column(nullable = false, columnDefinition = "VARCHAR(80)")
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, columnDefinition = "CHAR(4)")
    private String role;

    @ManyToMany(mappedBy = "pessoas")
    private Set<LivroModel> livros = new LinkedHashSet<>();

    public PessoaModel(String nome, String cep, String login, String senha, String role) {
        this.nome = nome;
        this.cep = cep;
        this.login = login;
        this.senha = senha;
        this.role = role;
    }

    public UUID getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(UUID idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @JsonManagedReference
    public Set<LivroModel> getLivros() {
        return livros;
    }

    public void setLivros(Set<LivroModel> livros) {
        this.livros = livros;
    }

    public void addLivro(LivroModel livro) {
        livros.add(livro);
        livro.getPessoas().add(this);
    }

    public void removeLivro(LivroModel livro) {
        livros.remove(livro);
        livro.getPessoas().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == "admin")
            return List.of(new SimpleGrantedAuthority("CARGO_ADMIN"), new SimpleGrantedAuthority("CARGO_USER"));
        else
            return List.of(new SimpleGrantedAuthority("CARGO_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
