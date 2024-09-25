package co.umpisa.exceptions;

// TODO exception type
public class ReservationException extends RuntimeException {

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(String message) {
        super(message);
    }
}
