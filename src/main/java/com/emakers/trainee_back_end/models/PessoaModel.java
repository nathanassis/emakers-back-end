package com.emakers.trainee_back_end.models;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Pessoa")
public class PessoaModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID idPessoa;

    @Column(nullable = false, columnDefinition = "VARCHAR(80)")
    private String nome;

    @Column(nullable = false, columnDefinition = "CHAR(9)")
    private String cep;

    @ManyToMany(mappedBy = "pessoas")
    private Set<LivroModel> livros = new LinkedHashSet<>();

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
}
