package br.edu.eseg.biblioteca.dto;

import java.time.LocalDate;

/** Corpo opcional do PATCH /api/emprestimos/{id}/devolucao. Sem data, vale hoje. */
public record DevolucaoRequest(LocalDate dataDevolucao) {
}
