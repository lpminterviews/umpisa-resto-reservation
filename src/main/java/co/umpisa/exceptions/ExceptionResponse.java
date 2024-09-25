package co.umpisa.exceptions;

import java.time.ZonedDateTime;

public class ExceptionResponse {

    private String message;
    private ZonedDateTime timestamp;

    public ExceptionResponse(Exception exception) {
        this.message = exception.getMessage();
        this.timestamp = ZonedDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
