package roomescape.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.repository.MemoryReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final MemoryReservationRepository reservationRepository = new MemoryReservationRepository();

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        reservationRepository.save(new Reservation("브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")));
        reservationRepository.save(new Reservation("브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00")));
        reservationRepository.save(new Reservation("브라운", LocalDate.parse("2023-01-03"), LocalTime.parse("12:00")));

    }
}
