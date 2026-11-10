package br.edu.eseg.biblioteca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.biblioteca.dto.EmprestimoResponse;
import br.edu.eseg.biblioteca.dto.LeitorRequest;
import br.edu.eseg.biblioteca.dto.LeitorResponse;
import br.edu.eseg.biblioteca.exception.RecursoNaoEncontradoException;
import br.edu.eseg.biblioteca.exception.RegraNegocioException;
import br.edu.eseg.biblioteca.model.Leitor;
import br.edu.eseg.biblioteca.repository.EmprestimoRepository;
import br.edu.eseg.biblioteca.repository.LeitorRepository;

@Service
public class LeitorService {

    private final LeitorRepository leitores;
    private final EmprestimoRepository emprestimos;

    public LeitorService(LeitorRepository leitores, EmprestimoRepository emprestimos) {
        this.leitores = leitores;
        this.emprestimos = emprestimos;
    }

    @Transactional(readOnly = true)
    public List<LeitorResponse> listar(String tipo) {
        List<Leitor> lista = (tipo == null) ? leitores.findAllByOrderByNome() : leitores.findByTipoOrderByNome(tipo);
        return lista.stream().map(LeitorResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public LeitorResponse buscar(Long id) {
        return leitores.findById(id).map(LeitorResponse::de)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leitor", id));
    }

    @Transactional(readOnly = true)
    public List<EmprestimoResponse> emprestimos(Long id) {
        if (!leitores.existsById(id)) {
            throw new RecursoNaoEncontradoException("Leitor", id);
        }
        return emprestimos.doLeitor(id).stream().map(EmprestimoResponse::de).toList();
    }

    @Transactional
    public LeitorResponse cadastrar(LeitorRequest req) {
        // RN02 (tipo válido) já foi verificada pelo @Pattern do LeitorRequest
        // RN01: email único (também garantido pelo UNIQUE do banco)
        if (leitores.existsByEmailIgnoreCase(req.email())) {
            throw new RegraNegocioException("RN01", HttpStatus.CONFLICT,
                    "Já existe um leitor com o email " + req.email());
        }
        Leitor novo = leitores.save(new Leitor(req.nome(), req.email(), req.tipo(), LocalDate.now()));
        return LeitorResponse.de(novo);
    }
}
