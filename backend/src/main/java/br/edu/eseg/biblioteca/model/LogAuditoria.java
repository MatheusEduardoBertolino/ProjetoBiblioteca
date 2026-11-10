package br.edu.eseg.biblioteca.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Tabela preenchida só pelos triggers da Parte 2. A API apenas lê. */
@Entity
@Immutable
@Table(name = "log_auditoria")
public class LogAuditoria {

    @Id
    @Column(name = "id_log")
    private Long id;

    private String tabela;

    private String operacao;

    @Column(name = "id_registro")
    private Long idRegistro;

    @Column(name = "valor_antigo")
    private String valorAntigo;

    @Column(name = "valor_novo")
    private String valorNovo;

    @Column(name = "data_hora")
    private String dataHora;

    protected LogAuditoria() {
    }

    public Long getId() {
        return id;
    }

    public String getTabela() {
        return tabela;
    }

    public String getOperacao() {
        return operacao;
    }

    public Long getIdRegistro() {
        return idRegistro;
    }

    public String getValorAntigo() {
        return valorAntigo;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public String getDataHora() {
        return dataHora;
    }
}
