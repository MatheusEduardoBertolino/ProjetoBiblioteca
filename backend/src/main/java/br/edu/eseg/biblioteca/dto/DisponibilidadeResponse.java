package br.edu.eseg.biblioteca.dto;

/** Q6 */
public record DisponibilidadeResponse(Long idLivro, String titulo, Integer exemplares, Long emprestados,
                                      Long disponiveis) {
}
