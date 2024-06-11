package com.emakers.trainee_back_end.dtos;

import jakarta.validation.constraints.NotBlank;

public record PessoaDto(
        @NotBlank String nome,
        @NotBlank String cep) {

}
