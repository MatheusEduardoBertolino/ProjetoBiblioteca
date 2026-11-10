package br.edu.eseg.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
