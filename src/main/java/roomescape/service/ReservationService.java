package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationSaveCommand;
import roomescape.service.dto.ReservationResult;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDAO) {
        this.reservationDao = reservationDAO;
    }

    public ReservationResult save(ReservationSaveCommand command) {
        Reservation reservation = command.toEntity();
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResult.from(savedReservation);
    }

    public List<ReservationResult> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResult::from)
                .toList();
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
