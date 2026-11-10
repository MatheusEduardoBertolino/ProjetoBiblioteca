package br.edu.eseg.biblioteca.dto;

import br.edu.eseg.biblioteca.model.Livro;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        String isbn,
        Integer anoEdicao,
        Integer qtdExemplares,
        String categoria,
        String editora) {

    public static LivroResponse de(Livro l) {
        return new LivroResponse(l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getAnoEdicao(),
                l.getQtdExemplares(), l.getCategoria().getNome(), l.getEditora().getNome());
    }
}
