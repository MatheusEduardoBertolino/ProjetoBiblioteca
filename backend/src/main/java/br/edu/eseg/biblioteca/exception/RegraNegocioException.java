package br.edu.eseg.biblioteca.exception;

import org.springframework.http.HttpStatus;

/** Violação de uma regra de negócio. Carrega o código da regra e o status HTTP. */
public class RegraNegocioException extends RuntimeException {

    private final String regra;
    private final HttpStatus status;

    public RegraNegocioException(String regra, HttpStatus status, String mensagem) {
        super(mensagem);
        this.regra = regra;
        this.status = status;
    }

    public String getRegra() {
        return regra;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
