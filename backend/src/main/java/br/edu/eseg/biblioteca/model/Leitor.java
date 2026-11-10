package br.edu.eseg.biblioteca.model;

import java.time.LocalDate;

import br.edu.eseg.biblioteca.model.converter.DataTextoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leitor")
public class Leitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leitor")
    private Long id;

    private String nome;

    private String email;

    /** aluno, professor ou funcionario (CHECK no banco, RN02). */
    private String tipo;

    @Convert(converter = DataTextoConverter.class)
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    protected Leitor() {
    }

    public Leitor(String nome, String email, String tipo, LocalDate dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}
