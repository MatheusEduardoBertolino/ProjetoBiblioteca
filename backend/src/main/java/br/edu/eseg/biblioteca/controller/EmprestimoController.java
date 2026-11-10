package br.edu.eseg.biblioteca.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.biblioteca.dto.DevolucaoRequest;
import br.edu.eseg.biblioteca.dto.EmprestimoRequest;
import br.edu.eseg.biblioteca.dto.EmprestimoResponse;
import br.edu.eseg.biblioteca.model.SituacaoEmprestimo;
import br.edu.eseg.biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    /** Lê a view. GET /api/emprestimos?situacao=atrasado  (ou devolvido, ou em dia) */
    @GetMapping
    public List<SituacaoEmprestimo> listar(@RequestParam(required = false) String situacao) {
        return service.listar(situacao);
    }

    @GetMapping("/{id}")
    public SituacaoEmprestimo buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** POST /api/emprestimos  {"idLivro": 3, "idLeitor": 2} */
    @PostMapping
    public ResponseEntity<EmprestimoResponse> emprestar(@Valid @RequestBody EmprestimoRequest req) {
        EmprestimoResponse novo = service.emprestar(req);
        return ResponseEntity.created(URI.create("/api/emprestimos/" + novo.id())).body(novo);
    }

    /** PATCH /api/emprestimos/41/devolucao  com corpo opcional {"dataDevolucao": "2026-11-20"} */
    @PatchMapping("/{id}/devolucao")
    public EmprestimoResponse devolver(@PathVariable Long id, @RequestBody(required = false) DevolucaoRequest req) {
        return service.devolver(id, req);
    }
}
