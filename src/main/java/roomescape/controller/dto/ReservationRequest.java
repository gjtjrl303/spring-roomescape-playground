package roomescape.controller.dto;

import roomescape.service.dto.SaveReservationCommand;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public SaveReservationCommand toCommand() {
        return new SaveReservationCommand(name, date, time);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
    }
}

