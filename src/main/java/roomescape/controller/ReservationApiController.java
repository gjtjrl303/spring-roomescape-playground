package roomescape.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.mapper.ReservationMapper;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationResult;
import roomescape.service.dto.SaveReservationCommand;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationApiController {

    private final ReservationService reservationService;
    private final ReservationMapper mapper;

    public ReservationApiController(ReservationService reservationService, ReservationMapper mapper) {
        this.reservationService = reservationService;
        this.mapper = mapper;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<ReservationResult> all = reservationService.findAll();
        List<ReservationResponse> reservationResponses = all.stream()
                .map(mapper::ResultToResponse)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> save(@RequestBody ReservationRequest reservationRequest) {
        SaveReservationCommand command = mapper.requestToCommand(reservationRequest);
        ReservationResult result = reservationService.save(command);
        ReservationResponse reservationResponse = mapper.ResultToResponse(result);
        return ResponseEntity.created(URI.create("/reservations/" + result.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(reservationResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
