package br.edu.eseg.biblioteca.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Mapeia a VIEW vw_situacao_emprestimo da Parte 2.
 * @Immutable: somente leitura, como toda view no SQLite.
 * As datas ficam como texto porque a view já devolve o formato AAAA-MM-DD.
 */
@Entity
@Immutable
@Table(name = "vw_situacao_emprestimo")
public class SituacaoEmprestimo {

    @Id
    @Column(name = "id_emprestimo")
    private Long idEmprestimo;

    private String leitor;

    @Column(name = "tipo_leitor")
    private String tipoLeitor;

    private String titulo;

    private String categoria;

    @Column(name = "data_emprestimo")
    private String dataEmprestimo;

    @Column(name = "data_prevista_devolucao")
    private String dataPrevistaDevolucao;

    @Column(name = "data_devolucao")
    private String dataDevolucao;

    /** devolvido, atrasado ou em dia. */
    private String situacao;

    @Column(name = "dias_atraso")
    private Integer diasAtraso;

    protected SituacaoEmprestimo() {
    }

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public String getLeitor() {
        return leitor;
    }

    public String getTipoLeitor() {
        return tipoLeitor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public String getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public String getSituacao() {
        return situacao;
    }

    public Integer getDiasAtraso() {
        return diasAtraso;
    }
}
