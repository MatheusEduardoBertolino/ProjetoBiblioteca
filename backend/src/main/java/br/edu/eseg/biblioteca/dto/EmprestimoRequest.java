package br.edu.eseg.biblioteca.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

/**
 * Corpo do POST /api/emprestimos.
 * As datas são opcionais: sem elas, o empréstimo é feito hoje com devolução em 14 dias.
 */
public record EmprestimoRequest(
        @NotNull(message = "é obrigatório") Long idLivro,
        @NotNull(message = "é obrigatório") Long idLeitor,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao) {
}
