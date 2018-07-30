package fr.plaisance.perudo.exception;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private String message, reason;
    private Integer status;

    private ErrorDetails() {}

    public static ErrorDetails of(String message, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.message = message;
        errorDetails.reason = status.getReasonPhrase();
        errorDetails.status = status.value();
        return errorDetails;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    public Integer getStatus() {
        return status;
    }
}
