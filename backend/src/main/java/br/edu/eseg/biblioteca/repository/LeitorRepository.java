package br.edu.eseg.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.biblioteca.model.Leitor;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    /** Consulta gerada pelo nome do método: ... where tipo = ? order by nome */
    List<Leitor> findByTipoOrderByNome(String tipo);

    List<Leitor> findAllByOrderByNome();

    /** RN01: já existe leitor com este email? */
    boolean existsByEmailIgnoreCase(String email);
}
