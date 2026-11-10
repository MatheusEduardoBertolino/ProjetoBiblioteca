package br.edu.eseg.biblioteca.dto;

import java.time.LocalDate;

import br.edu.eseg.biblioteca.model.Emprestimo;

public record EmprestimoResponse(
        Long id,
        Long idLivro,
        String titulo,
        Long idLeitor,
        String leitor,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao) {

    public static EmprestimoResponse de(Emprestimo e) {
        return new EmprestimoResponse(e.getId(),
                e.getLivro().getId(), e.getLivro().getTitulo(),
                e.getLeitor().getId(), e.getLeitor().getNome(),
                e.getDataEmprestimo(), e.getDataPrevistaDevolucao(), e.getDataDevolucao());
    }
}
