package roomescape.service.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationResult(
        Long id,
        String name,
        LocalDate date,
        Time time
) {
    public static ReservationResult from(Reservation reservation) {
        return new ReservationResult(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
