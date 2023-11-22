package it.ecubit.xmpp.services.util;

import it.ecubit.xmpp.services.enums.ErrorCode;
import it.ecubit.xmpp.services.exception.BadRequestException;
import it.ecubit.xmpp.services.rest.entity.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLSyntaxErrorException;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request) {
        Error error = Error.create(
                ErrorCode.HTTP_MESSAGE_NOT_READABLE.getErrorMessage(),
                HttpStatus.BAD_REQUEST.value());
        error.requestMethod(request.getMethod()).timestamp(Instant.now()).setUrl(request.getRequestURL().toString());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<Error> handleSQLSyntaxErrorException(HttpServletRequest request) {
        Error error = Error.create(
                ErrorCode.SQL_SYNTAX_ERROR.getErrorMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY.value());
        error.requestMethod(request.getMethod()).timestamp(Instant.now()).setUrl(request.getRequestURL().toString());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handleExceptionGeneric(HttpServletRequest request, BadRequestException e) {
        Error error = Error.create(
                (e.getMessage() == null || e.getMessage().isEmpty()) ? ErrorCode.BAD_REQUEST_MESSAGE.getErrorMessage() : e.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        error.requestMethod(request.getMethod()).timestamp(Instant.now()).setUrl(request.getRequestURL().toString());
        log.error(error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ItemNotFoundException.class)
//    public ResponseEntity<Error> handleItemNotFoundException(HttpServletRequest request) {
//        Error error = Error.create(
//                ErrorCode.ITEM_NOT_FOUND.getErrorMessage(),
//                HttpStatus.NOT_FOUND.value());
//        error.requestMethod(request.getMethod()).timestamp(Instant.now()).setUrl(request.getRequestURL().toString());
//        log.error(error.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

}
