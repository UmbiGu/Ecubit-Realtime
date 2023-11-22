package it.ecubit.xmpp.services.rest.entity;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;

@Data
public class Error {

    private String message;
    private Integer status;
    private String url = "Not available";
    private String reqMethod = "Not available";
    private Instant timestamp;


    public Error requestMethod(String method) {
        if (Strings.isNotBlank(method)) this.reqMethod = method;
        return this;
    }

    public Error timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public static Error create(final String errMsgKey, final Integer httpStatusCode) {
        Error error = new Error();
        error.setMessage(errMsgKey);
        error.setStatus(httpStatusCode);
        return error;
    }

}
