package br.edu.eseg.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.biblioteca.model.Editora;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
}
