package com.emakers.trainee_back_end.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emakers.trainee_back_end.models.PessoaModel;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, UUID> {
}
