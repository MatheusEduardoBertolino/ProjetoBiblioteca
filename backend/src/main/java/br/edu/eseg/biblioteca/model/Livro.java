package br.edu.eseg.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @Column(name = "id_livro")
    private Long id;

    private String titulo;

    private String autor;

    private String isbn;

    @Column(name = "ano_edicao")
    private Integer anoEdicao;

    @Column(name = "qtd_exemplares")
    private Integer qtdExemplares;

    /** N:1 com categoria: a coluna id_categoria é a chave estrangeira. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editora")
    private Editora editora;

    protected Livro() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAnoEdicao() {
        return anoEdicao;
    }

    public Integer getQtdExemplares() {
        return qtdExemplares;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Editora getEditora() {
        return editora;
    }
}
