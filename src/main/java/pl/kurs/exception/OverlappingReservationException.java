package pl.kurs.exception;

public class OverlappingReservationException extends RuntimeException {
    public OverlappingReservationException(String message) {
        super(message);
    }
}
