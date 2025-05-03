package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.*;

public class MemoryReservationRepository {

    private static Map<Long, Reservation> store = new LinkedHashMap<>();
    private static Long sequence = 0L;

    public void save(Reservation reservation) {
        if (reservation.getId() != null) {
            throw new IllegalStateException("이미 ID가 존재하는 예약은 저장할 수 없습니다.");
        }
        Reservation saved = reservation.withId(++sequence);
        store.put(saved.getId(), saved);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }
}
