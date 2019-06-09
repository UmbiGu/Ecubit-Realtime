package it.almaviva.nerds.services.controller;

public class InterventoException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public InterventoException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public InterventoException() {
        super();
    }
}
