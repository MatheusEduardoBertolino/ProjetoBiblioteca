package br.edu.eseg.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.biblioteca.model.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /** RN05: empréstimos em aberto do leitor. */
    long countByLeitor_IdAndDataDevolucaoIsNull(Long idLeitor);

    /** RN06: empréstimos em aberto do livro. */
    long countByLivro_IdAndDataDevolucaoIsNull(Long idLivro);

    @Query("""
            select e from Emprestimo e
            join fetch e.livro
            join fetch e.leitor
            where e.id = :id
            """)
    Optional<Emprestimo> buscarCompleto(@Param("id") Long id);

    /** Histórico de um leitor, do mais recente para o mais antigo. */
    @Query("""
            select e from Emprestimo e
            join fetch e.livro
            join fetch e.leitor le
            where le.id = :idLeitor
            order by e.dataEmprestimo desc
            """)
    List<Emprestimo> doLeitor(@Param("idLeitor") Long idLeitor);

    /** Q4: total de empréstimos por categoria, inclusive as que têm zero (LEFT JOIN). */
    @Query("""
            select c.nome, count(e.id)
            from Categoria c
            left join Livro l on l.categoria = c
            left join Emprestimo e on e.livro = l
            group by c.id, c.nome
            order by count(e.id) desc, c.nome
            """)
    List<Object[]> totalPorCategoria();

    /** Q6: exemplares disponíveis por livro (condição de "em aberto" dentro do ON). */
    @Query("""
            select l.id, l.titulo, l.qtdExemplares, count(e.id)
            from Livro l
            left join Emprestimo e on e.livro = l and e.dataDevolucao is null
            group by l.id, l.titulo, l.qtdExemplares
            order by l.titulo
            """)
    List<Object[]> disponibilidade();
}
