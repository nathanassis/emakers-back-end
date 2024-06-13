package com.emakers.trainee_back_end.models;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "Livro")
public class LivroModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private UUID idLivro;

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    private String nome;

    @Column(nullable = false, columnDefinition = "VARCHAR(45)")
    private String autor;

    @Column(columnDefinition = "DATE")
    private Date dataLancamento;

    @Column(nullable = false, columnDefinition = "INT")
    private Integer quantidade;

    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "emprestimo", joinColumns = @JoinColumn(name = "id_livro"), inverseJoinColumns = @JoinColumn(name = "id_pessoa"))
    private Set<PessoaModel> pessoas = new LinkedHashSet<>();

    public UUID getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(UUID idLivro) {
        this.idLivro = idLivro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @JsonBackReference
    public Set<PessoaModel> getPessoas() {
        return pessoas;
    }

    public void setPessoas(Set<PessoaModel> pessoas) {
        this.pessoas = pessoas;
    }
}
