package roomescape.controller.dto;

import roomescape.domain.Time;
import roomescape.service.dto.ReservationResult;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        Time time
) {

    public static ReservationResponse from(ReservationResult result) {
        return new ReservationResponse(
                result.id(),
                result.name(),
                result.date(),
                result.time()
        );
    }
}
