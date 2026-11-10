package br.edu.eseg.biblioteca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.biblioteca.dto.DevolucaoRequest;
import br.edu.eseg.biblioteca.dto.EmprestimoRequest;
import br.edu.eseg.biblioteca.dto.EmprestimoResponse;
import br.edu.eseg.biblioteca.exception.RecursoNaoEncontradoException;
import br.edu.eseg.biblioteca.exception.RegraNegocioException;
import br.edu.eseg.biblioteca.model.Emprestimo;
import br.edu.eseg.biblioteca.model.Leitor;
import br.edu.eseg.biblioteca.model.Livro;
import br.edu.eseg.biblioteca.model.SituacaoEmprestimo;
import br.edu.eseg.biblioteca.repository.EmprestimoRepository;
import br.edu.eseg.biblioteca.repository.LeitorRepository;
import br.edu.eseg.biblioteca.repository.LivroRepository;
import br.edu.eseg.biblioteca.repository.SituacaoEmprestimoRepository;

/**
 * Aqui ficam as regras que o banco não garante sozinho:
 * RN05 (limite de 3 em aberto por leitor) e RN06 (exemplar disponível).
 */
@Service
public class EmprestimoService {

    public static final int LIMITE_EM_ABERTO = 3;
    public static final int PRAZO_PADRAO_DIAS = 14;

    private final EmprestimoRepository emprestimos;
    private final LivroRepository livros;
    private final LeitorRepository leitores;
    private final SituacaoEmprestimoRepository situacoes;

    public EmprestimoService(EmprestimoRepository emprestimos, LivroRepository livros, LeitorRepository leitores,
                             SituacaoEmprestimoRepository situacoes) {
        this.emprestimos = emprestimos;
        this.livros = livros;
        this.leitores = leitores;
        this.situacoes = situacoes;
    }

    /** Lê a view. situacao opcional: devolvido, atrasado ou "em dia". */
    @Transactional(readOnly = true)
    public List<SituacaoEmprestimo> listar(String situacao) {
        return (situacao == null)
                ? situacoes.findAllByOrderByIdEmprestimoDesc()
                : situacoes.findBySituacaoOrderByDiasAtrasoDescIdEmprestimoDesc(situacao);
    }

    @Transactional(readOnly = true)
    public SituacaoEmprestimo buscar(Long id) {
        return situacoes.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo", id));
    }

    @Transactional
    public EmprestimoResponse emprestar(EmprestimoRequest req) {
        // RN04: livro e leitor precisam existir
        Livro livro = livros.findById(req.idLivro())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro", req.idLivro()));
        Leitor leitor = leitores.findById(req.idLeitor())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Leitor", req.idLeitor()));

        LocalDate inicio = (req.dataEmprestimo() != null) ? req.dataEmprestimo() : LocalDate.now();
        LocalDate prevista = (req.dataPrevistaDevolucao() != null)
                ? req.dataPrevistaDevolucao() : inicio.plusDays(PRAZO_PADRAO_DIAS);

        // RN03: devolução prevista depois do empréstimo
        if (!prevista.isAfter(inicio)) {
            throw new RegraNegocioException("RN03", HttpStatus.UNPROCESSABLE_CONTENT,
                    "A devolução prevista (" + prevista + ") precisa ser depois do empréstimo (" + inicio + ")");
        }

        // RN05: no máximo 3 empréstimos em aberto por leitor
        long abertosLeitor = emprestimos.countByLeitor_IdAndDataDevolucaoIsNull(leitor.getId());
        if (abertosLeitor >= LIMITE_EM_ABERTO) {
            throw new RegraNegocioException("RN05", HttpStatus.UNPROCESSABLE_CONTENT,
                    leitor.getNome() + " já tem " + abertosLeitor + " empréstimos em aberto (limite "
                            + LIMITE_EM_ABERTO + ")");
        }

        // RN06: precisa haver exemplar disponível
        long abertosLivro = emprestimos.countByLivro_IdAndDataDevolucaoIsNull(livro.getId());
        if (abertosLivro >= livro.getQtdExemplares()) {
            throw new RegraNegocioException("RN06", HttpStatus.UNPROCESSABLE_CONTENT,
                    "Nenhum exemplar disponível de \"" + livro.getTitulo() + "\": " + abertosLivro + " de "
                            + livro.getQtdExemplares() + " estão emprestados");
        }

        Emprestimo novo = emprestimos.save(new Emprestimo(livro, leitor, inicio, prevista));
        return EmprestimoResponse.de(novo);
    }

    @Transactional
    public EmprestimoResponse devolver(Long id, DevolucaoRequest req) {
        Emprestimo e = emprestimos.buscarCompleto(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empréstimo", id));
        if (!e.emAberto()) {
            throw new RegraNegocioException("DEVOLUCAO", HttpStatus.CONFLICT,
                    "O empréstimo " + id + " já foi devolvido em " + e.getDataDevolucao());
        }
        LocalDate data = (req != null && req.dataDevolucao() != null) ? req.dataDevolucao() : LocalDate.now();
        // RN03: a devolução nunca é antes do empréstimo
        if (data.isBefore(e.getDataEmprestimo())) {
            throw new RegraNegocioException("RN03", HttpStatus.UNPROCESSABLE_CONTENT,
                    "A devolução (" + data + ") não pode ser antes do empréstimo (" + e.getDataEmprestimo() + ")");
        }
        e.registrarDevolucao(data);
        return EmprestimoResponse.de(emprestimos.saveAndFlush(e));
    }
}
