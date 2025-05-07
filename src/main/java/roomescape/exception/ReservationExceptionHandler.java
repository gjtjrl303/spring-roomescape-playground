package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.controller.ReservationApiController;

@RestControllerAdvice(basePackageClasses = ReservationApiController.class)
public class ReservationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalExceptionHandler(IllegalArgumentException e) {
        ErrorResult errorResult = new ErrorResult("BAD_REQUEST", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResult> notFoundReservationHandler(NotFoundReservationException e) {
        ErrorResult errorResult = new ErrorResult("NOT_FOUND_RESERVATION", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> handleJsonParseError(HttpMessageNotReadableException e) {
        ErrorResult errorResult = new ErrorResult("INVALID_JSON", "형식이 옳바르지 않습니다");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
