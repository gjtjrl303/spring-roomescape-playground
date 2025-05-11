package roomescape.controller.dto;

import roomescape.service.dto.ReservationResult;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponse from(ReservationResult result) {
        return new ReservationResponse(
                result.id(),
                result.name(),
                result.date(),
                result.time()
        );
    }}
