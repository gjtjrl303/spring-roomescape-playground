package roomescape.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResult(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
}
