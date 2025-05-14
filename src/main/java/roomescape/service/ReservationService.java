package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCommand;
import roomescape.service.dto.ReservationResult;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public ReservationResult save(ReservationCommand command) {
        Reservation reservation = command.toEntity();
        Reservation savedReservation = reservationDAO.save(reservation);
        return ReservationResult.from(savedReservation);
    }

    public List<ReservationResult> findAll() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations.stream()
                .map(ReservationResult::from)
                .toList();
    }

    public void delete(Long id) {
        reservationDAO.delete(id);
    }
}
