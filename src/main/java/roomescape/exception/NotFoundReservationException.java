package roomescape.exception;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(String string) {
        super(string);
    }
}
