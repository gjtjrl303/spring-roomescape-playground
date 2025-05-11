package roomescape.service.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record SaveReservationCommand(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toEntity() {
        return new Reservation(name, date, time);
    }
}
