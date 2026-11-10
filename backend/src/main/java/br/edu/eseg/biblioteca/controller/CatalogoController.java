package br.edu.eseg.biblioteca.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.biblioteca.dto.LivroResponse;
import br.edu.eseg.biblioteca.model.Categoria;
import br.edu.eseg.biblioteca.model.Editora;
import br.edu.eseg.biblioteca.service.CatalogoService;

@RestController
@RequestMapping("/api")
public class CatalogoController {

    private final CatalogoService service;

    public CatalogoController(CatalogoService service) {
        this.service = service;
    }

    @GetMapping("/categorias")
    public List<Categoria> categorias() {
        return service.categorias();
    }

    @GetMapping("/editoras")
    public List<Editora> editoras() {
        return service.editoras();
    }

    /** GET /api/livros  ou  /api/livros?idCategoria=1  ou  /api/livros?titulo=dados */
    @GetMapping("/livros")
    public List<LivroResponse> livros(@RequestParam(required = false) Long idCategoria,
                                      @RequestParam(required = false) String titulo) {
        return service.livros(idCategoria, titulo);
    }

    @GetMapping("/livros/{id}")
    public LivroResponse livro(@PathVariable Long id) {
        return service.livro(id);
    }
}
