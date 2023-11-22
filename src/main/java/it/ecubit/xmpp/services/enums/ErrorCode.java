package it.ecubit.xmpp.services.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    HTTP_MESSAGE_NOT_READABLE("Assicurati di aver allegato l'oggetto nel body della chiamata."),
    ITEM_NOT_FOUND("L'oggetto ricercato non risulta essere presente nel database."),
    SQL_SYNTAX_ERROR("Almeno un campo dell'oggetto inviato non è conforme allo standard."),
    BAD_REQUEST_MESSAGE("Bad Request generic message!");

    private String errorMessage;
}
