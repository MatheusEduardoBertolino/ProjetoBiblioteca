package br.edu.eseg.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.biblioteca.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    /** Livros com categoria e editora (JOIN), com filtros opcionais. */
    @Query("""
            select l from Livro l
            join fetch l.categoria c
            join fetch l.editora e
            where (:idCategoria is null or c.id = :idCategoria)
              and (:titulo is null or lower(l.titulo) like lower(concat('%', :titulo, '%')))
            order by l.titulo
            """)
    List<Livro> buscar(@Param("idCategoria") Long idCategoria, @Param("titulo") String titulo);

    @Query("""
            select l from Livro l
            join fetch l.categoria
            join fetch l.editora
            where l.id = :id
            """)
    Optional<Livro> buscarCompleto(@Param("id") Long id);

    /** Q3: livros que nunca foram emprestados. */
    @Query("""
            select l from Livro l
            join fetch l.categoria
            join fetch l.editora
            where not exists (select 1 from Emprestimo e where e.livro = l)
            order by l.titulo
            """)
    List<Livro> nuncaEmprestados();
}
