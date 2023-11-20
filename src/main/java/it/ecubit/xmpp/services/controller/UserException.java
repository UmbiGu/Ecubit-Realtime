package it.ecubit.xmpp.services.controller;

public class UserException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public UserException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public UserException() {
        super();
    }
}
