package roomescape.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SaveReservationCommand(
        String name,
        LocalDate date,
        LocalTime time
) {}
