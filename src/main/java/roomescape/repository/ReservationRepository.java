package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationAlreadyExistsException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private static Map<Long, Reservation> store = new HashMap<>();

    private AtomicLong index = new AtomicLong(0);

    public Reservation save(Reservation reservation) {
        if (reservation.getId() != null) {
            throw new ReservationAlreadyExistsException("이미 ID가 존재하는 예약은 저장할 수 없습니다.");
        }
        Reservation saved = reservation.withId(index.incrementAndGet());
        store.put(saved.getId(), saved);
        return saved;
    }

    public List<Reservation>    findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long id) {
        if (!store.containsKey(id)) {
            throw new NotFoundReservationException("해당 예약이 존재하지 않습니다.");
        }
        store.remove(id);
    }
}
