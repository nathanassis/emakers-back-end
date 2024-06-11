package com.emakers.trainee_back_end.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroDto(
        @NotBlank String nome,
        @NotBlank String autor,
        @NotNull Date dataLancamento) {
}
