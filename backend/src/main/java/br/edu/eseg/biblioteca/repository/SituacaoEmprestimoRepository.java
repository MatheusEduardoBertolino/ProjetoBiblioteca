package br.edu.eseg.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.biblioteca.model.SituacaoEmprestimo;

/** Consultas sobre a view vw_situacao_emprestimo: nenhuma precisa de JOIN. */
public interface SituacaoEmprestimoRepository extends JpaRepository<SituacaoEmprestimo, Long> {

    List<SituacaoEmprestimo> findAllByOrderByIdEmprestimoDesc();

    List<SituacaoEmprestimo> findBySituacaoOrderByDiasAtrasoDescIdEmprestimoDesc(String situacao);
}
