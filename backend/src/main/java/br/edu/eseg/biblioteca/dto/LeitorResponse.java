package br.edu.eseg.biblioteca.dto;

import java.time.LocalDate;

import br.edu.eseg.biblioteca.model.Leitor;

public record LeitorResponse(Long id, String nome, String email, String tipo, LocalDate dataCadastro) {

    public static LeitorResponse de(Leitor l) {
        return new LeitorResponse(l.getId(), l.getNome(), l.getEmail(), l.getTipo(), l.getDataCadastro());
    }
}
