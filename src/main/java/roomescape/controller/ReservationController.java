package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private static Long sequence = 0L;

    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> findAll() {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        reservations.add(new Reservation(++sequence, "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")));
        reservations.add(new Reservation(++sequence, "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00")));
        reservations.add(new Reservation(++sequence, "브라운", LocalDate.parse("2023-01-03"), LocalTime.parse("12:00")));
    }
}
