package br.edu.eseg.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.biblioteca.dto.CategoriaTotalResponse;
import br.edu.eseg.biblioteca.dto.DisponibilidadeResponse;
import br.edu.eseg.biblioteca.dto.LivroResponse;
import br.edu.eseg.biblioteca.model.LogAuditoria;
import br.edu.eseg.biblioteca.model.SituacaoEmprestimo;
import br.edu.eseg.biblioteca.repository.EmprestimoRepository;
import br.edu.eseg.biblioteca.repository.LivroRepository;
import br.edu.eseg.biblioteca.repository.LogAuditoriaRepository;
import br.edu.eseg.biblioteca.repository.SituacaoEmprestimoRepository;

/** As consultas das Partes 1 e 2 expostas como relatórios. */
@Service
@Transactional(readOnly = true)
public class RelatorioService {

    private final SituacaoEmprestimoRepository situacoes;
    private final EmprestimoRepository emprestimos;
    private final LivroRepository livros;
    private final LogAuditoriaRepository log;

    public RelatorioService(SituacaoEmprestimoRepository situacoes, EmprestimoRepository emprestimos,
                            LivroRepository livros, LogAuditoriaRepository log) {
        this.situacoes = situacoes;
        this.emprestimos = emprestimos;
        this.livros = livros;
        this.log = log;
    }

    /** Q2 e Q7: atrasados, do maior atraso para o menor. */
    public List<SituacaoEmprestimo> atrasados() {
        return situacoes.findBySituacaoOrderByDiasAtrasoDescIdEmprestimoDesc("atrasado");
    }

    /** Q3 */
    public List<LivroResponse> nuncaEmprestados() {
        return livros.nuncaEmprestados().stream().map(LivroResponse::de).toList();
    }

    /** Q4 */
    public List<CategoriaTotalResponse> porCategoria() {
        return emprestimos.totalPorCategoria().stream()
                .map(l -> new CategoriaTotalResponse((String) l[0], ((Number) l[1]).longValue()))
                .toList();
    }

    /** Q6 */
    public List<DisponibilidadeResponse> disponibilidade() {
        return emprestimos.disponibilidade().stream().map(l -> {
            int exemplares = ((Number) l[2]).intValue();
            long emprestados = ((Number) l[3]).longValue();
            return new DisponibilidadeResponse(((Number) l[0]).longValue(), (String) l[1], exemplares, emprestados,
                    exemplares - emprestados);
        }).toList();
    }

    /** Log gravado pelos triggers da Parte 2, do mais recente para o mais antigo. */
    public List<LogAuditoria> auditoria() {
        return log.findAllByOrderByIdDesc();
    }
}
