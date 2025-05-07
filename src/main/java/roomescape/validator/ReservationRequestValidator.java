package roomescape.validator;

import roomescape.dto.ReservationRequest;

public class ReservationRequestValidator {

    public static void validate(ReservationRequest reservationRequest) {
        if (reservationRequest.name() == null || reservationRequest.name().trim().isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (reservationRequest.date() == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (reservationRequest.time() == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }
}
