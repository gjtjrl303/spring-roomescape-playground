package roomescape.exception;

public class CannotDeleteTimeWithExistingReservationException extends RuntimeException {
    public CannotDeleteTimeWithExistingReservationException(String message) {
        super(message);
    }
}
