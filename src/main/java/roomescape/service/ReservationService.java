package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResult;
import roomescape.service.dto.SaveReservationCommand;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResult> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResult::from)
                .toList();
    }

    public ReservationResult save(SaveReservationCommand command) {
        Reservation reservation = command.toEntity();
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResult.from(savedReservation);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
