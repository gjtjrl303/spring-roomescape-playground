package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        validateDate(date);
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

    public Long getId() {
        return id;
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

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("예약 날짜는 오늘 이후여야 합니다.");
        }
    }
}
