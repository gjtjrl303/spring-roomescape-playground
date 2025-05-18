package roomescape.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.dto.ReservationSaveCommand;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class ReservationDAOTest {

    @Autowired
    ReservationDao reservationDao;
    @Autowired DataSource dataSource;

    @Test
    void connection() {
        try (final var connection = dataSource.getConnection()){
            assertThat(connection).isNotNull();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
        Reservation reservation = new Reservation( "name", LocalDate.parse("2026-05-02"), LocalTime.parse("12:00"));
        Reservation savedReservation = reservationDao.save(reservation);

        assertThat(savedReservation.getName()).isEqualTo("name");
        assertThat(savedReservation.getDate()).isEqualTo(LocalDate.parse("2026-05-02"));
        assertThat(savedReservation.getTime()).isEqualTo(LocalTime.parse("12:00"));
    }

    @Test
    void findById() {
        //given
        Reservation reservation = new Reservation("name", LocalDate.parse("2026-05-02"), LocalTime.parse("12:00"));
        Reservation savedReservation = reservationDao.save(reservation);

        //when
        Reservation findReservation = reservationDao.findById(savedReservation.getId());

        //then
        assertThat(findReservation.getId()).isEqualTo(savedReservation.getId());
        assertThat(findReservation.getName()).isEqualTo(savedReservation.getName());
        assertThat(findReservation.getDate()).isEqualTo(savedReservation.getDate());
        assertThat(findReservation.getTime()).isEqualTo(savedReservation.getTime());
    }

    @Test
    void update() {
        //given
        Reservation reservation = new Reservation( "name", LocalDate.parse("2026-05-02"), LocalTime.parse("12:00"));
        Reservation savedReservation = reservationDao.save(reservation);
        ReservationSaveCommand reservationCommand = new ReservationSaveCommand("name1", LocalDate.parse("2026-05-03"), LocalTime.parse("13:00"));

        //when
        reservationDao.update(savedReservation.getId(), reservationCommand.toEntity());
        Reservation updatedReservation = reservationDao.findById(savedReservation.getId());
        //then
        assertThat(updatedReservation.getId()).isEqualTo(savedReservation.getId());
        assertThat(updatedReservation.getName()).isEqualTo(reservationCommand.name());
        assertThat(updatedReservation.getDate()).isEqualTo(reservationCommand.date());
        assertThat(updatedReservation.getTime()).isEqualTo(reservationCommand.time());
    }

    @Test
    void delete() {
        //given
        Reservation reservation = new Reservation( "name", LocalDate.parse("2026-05-02"), LocalTime.parse("12:00"));
        Reservation savedReservation = reservationDao.save(reservation);

        //when
        reservationDao.delete(savedReservation.getId());

        //then
        assertThatThrownBy(() -> reservationDao.findById(savedReservation.getId()))
                .isInstanceOf(NotFoundReservationException.class);
    }
}
