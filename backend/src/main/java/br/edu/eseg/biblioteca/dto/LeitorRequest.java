package br.edu.eseg.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Corpo do POST /api/leitores. */
public record LeitorRequest(
        @NotBlank(message = "é obrigatório") String nome,
        @NotBlank(message = "é obrigatório") @Email(message = "formato inválido") String email,
        @NotBlank(message = "é obrigatório")
        @Pattern(regexp = "aluno|professor|funcionario", message = "deve ser aluno, professor ou funcionario")
        String tipo) {
}
