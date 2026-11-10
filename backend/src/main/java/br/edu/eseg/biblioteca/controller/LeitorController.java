package br.edu.eseg.biblioteca.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.biblioteca.dto.EmprestimoResponse;
import br.edu.eseg.biblioteca.dto.LeitorRequest;
import br.edu.eseg.biblioteca.dto.LeitorResponse;
import br.edu.eseg.biblioteca.service.LeitorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leitores")
public class LeitorController {

    private final LeitorService service;

    public LeitorController(LeitorService service) {
        this.service = service;
    }

    /** GET /api/leitores  ou  /api/leitores?tipo=professor */
    @GetMapping
    public List<LeitorResponse> listar(@RequestParam(required = false) String tipo) {
        return service.listar(tipo);
    }

    @GetMapping("/{id}")
    public LeitorResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** Histórico de empréstimos do leitor. */
    @GetMapping("/{id}/emprestimos")
    public List<EmprestimoResponse> emprestimos(@PathVariable Long id) {
        return service.emprestimos(id);
    }

    /** POST /api/leitores  {"nome": "...", "email": "...", "tipo": "aluno"} */
    @PostMapping
    public ResponseEntity<LeitorResponse> cadastrar(@Valid @RequestBody LeitorRequest req) {
        LeitorResponse novo = service.cadastrar(req);
        return ResponseEntity.created(URI.create("/api/leitores/" + novo.id())).body(novo);
    }
}
