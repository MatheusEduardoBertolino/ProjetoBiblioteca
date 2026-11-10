package br.edu.eseg.biblioteca.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import br.edu.eseg.biblioteca.model.converter.DataTextoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Resolve o N:N entre leitor e livro, com atributos próprios (as datas).
 * @DynamicUpdate: o UPDATE só envia as colunas que mudaram (na devolução, só data_devolucao),
 * e assim o trigger trg_aud_emprestimo_devolucao enxerga exatamente o que aconteceu.
 */
@Entity
@DynamicUpdate
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_leitor")
    private Leitor leitor;

    @Convert(converter = DataTextoConverter.class)
    @Column(name = "data_emprestimo")
    private LocalDate dataEmprestimo;

    @Convert(converter = DataTextoConverter.class)
    @Column(name = "data_prevista_devolucao")
    private LocalDate dataPrevistaDevolucao;

    /** NULL significa que o livro ainda não voltou. */
    @Convert(converter = DataTextoConverter.class)
    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    protected Emprestimo() {
    }

    public Emprestimo(Livro livro, Leitor leitor, LocalDate dataEmprestimo, LocalDate dataPrevistaDevolucao) {
        this.livro = livro;
        this.leitor = leitor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public void registrarDevolucao(LocalDate data) {
        this.dataDevolucao = data;
    }

    public boolean emAberto() {
        return dataDevolucao == null;
    }

    public Long getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
}
