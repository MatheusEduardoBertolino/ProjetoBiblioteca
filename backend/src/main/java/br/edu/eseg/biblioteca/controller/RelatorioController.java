package br.edu.eseg.biblioteca.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.biblioteca.dto.CategoriaTotalResponse;
import br.edu.eseg.biblioteca.dto.DisponibilidadeResponse;
import br.edu.eseg.biblioteca.dto.LivroResponse;
import br.edu.eseg.biblioteca.model.LogAuditoria;
import br.edu.eseg.biblioteca.model.SituacaoEmprestimo;
import br.edu.eseg.biblioteca.service.RelatorioService;

@RestController
@RequestMapping("/api")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/relatorios/atrasados")
    public List<SituacaoEmprestimo> atrasados() {
        return service.atrasados();
    }

    @GetMapping("/relatorios/nunca-emprestados")
    public List<LivroResponse> nuncaEmprestados() {
        return service.nuncaEmprestados();
    }

    @GetMapping("/relatorios/por-categoria")
    public List<CategoriaTotalResponse> porCategoria() {
        return service.porCategoria();
    }

    @GetMapping("/relatorios/disponibilidade")
    public List<DisponibilidadeResponse> disponibilidade() {
        return service.disponibilidade();
    }

    /** O que os triggers da Parte 2 gravaram. */
    @GetMapping("/auditoria")
    public List<LogAuditoria> auditoria() {
        return service.auditoria();
    }
}
