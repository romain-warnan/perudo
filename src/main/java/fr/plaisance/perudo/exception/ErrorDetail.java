package fr.plaisance.perudo.exception;

import org.springframework.http.HttpStatus;

public class ErrorDetail {
    private String message, reason;
    private Integer status;

    private ErrorDetail() {}

    public static ErrorDetail of(String message, HttpStatus status) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.message = message;
        errorDetail.reason = status.getReasonPhrase();
        errorDetail.status = status.value();
        return errorDetail;
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
