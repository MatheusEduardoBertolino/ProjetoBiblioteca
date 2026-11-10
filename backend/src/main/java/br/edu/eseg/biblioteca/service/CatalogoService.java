package br.edu.eseg.biblioteca.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.biblioteca.dto.LivroResponse;
import br.edu.eseg.biblioteca.exception.RecursoNaoEncontradoException;
import br.edu.eseg.biblioteca.model.Categoria;
import br.edu.eseg.biblioteca.model.Editora;
import br.edu.eseg.biblioteca.repository.CategoriaRepository;
import br.edu.eseg.biblioteca.repository.EditoraRepository;
import br.edu.eseg.biblioteca.repository.LivroRepository;

/** Consultas do acervo: categorias, editoras e livros. */
@Service
@Transactional(readOnly = true)
public class CatalogoService {

    private final CategoriaRepository categorias;
    private final EditoraRepository editoras;
    private final LivroRepository livros;

    public CatalogoService(CategoriaRepository categorias, EditoraRepository editoras, LivroRepository livros) {
        this.categorias = categorias;
        this.editoras = editoras;
        this.livros = livros;
    }

    public List<Categoria> categorias() {
        return categorias.findAll(Sort.by("nome"));
    }

    public List<Editora> editoras() {
        return editoras.findAll(Sort.by("nome"));
    }

    public List<LivroResponse> livros(Long idCategoria, String titulo) {
        return livros.buscar(idCategoria, titulo).stream().map(LivroResponse::de).toList();
    }

    public LivroResponse livro(Long id) {
        return livros.buscarCompleto(id).map(LivroResponse::de)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro", id));
    }
}
