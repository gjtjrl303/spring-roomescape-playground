package roomescape.mapper;

import org.springframework.stereotype.Component;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationResult;
import roomescape.service.dto.SaveReservationCommand;

@Component
public class ReservationMapper {

    public SaveReservationCommand requestToCommand(ReservationRequest reservationRequest) {
        return new SaveReservationCommand(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTime()
        );
    }

    public Reservation commandToEntity(SaveReservationCommand reservationCommand) {
        return new Reservation(
                reservationCommand.name(),
                reservationCommand.date(),
                reservationCommand.time()
        );
    }

    public ReservationResult entityToResult(Reservation reservation) {
        return new ReservationResult(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public ReservationResponse ResultToResponse(ReservationResult result) {
        return new ReservationResponse(
                result.id(),
                result.name(),
                result.date(),
                result.time()
        );
    }
}
