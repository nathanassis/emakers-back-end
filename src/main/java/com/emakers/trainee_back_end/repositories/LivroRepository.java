package com.emakers.trainee_back_end.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emakers.trainee_back_end.models.LivroModel;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, UUID> {
}
