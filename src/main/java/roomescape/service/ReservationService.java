package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.mapper.ReservationMapper;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResult;
import roomescape.service.dto.SaveReservationCommand;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    public List<ReservationResult> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(mapper::entityToResult)
                .toList();
    }

    public ReservationResult save(SaveReservationCommand command) {
        Reservation reservation = mapper.commandToEntity(command);
        Reservation savedReservation = reservationRepository.save(reservation);
        return mapper.entityToResult(savedReservation);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
