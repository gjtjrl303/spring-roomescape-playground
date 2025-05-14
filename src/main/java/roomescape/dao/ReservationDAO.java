package roomescape.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.ReservationAlreadyExistsException;
import roomescape.service.dto.ReservationCommand;

import java.sql.*;

@Component
public class ReservationDAO {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public Reservation save(Reservation reservation) {

        String sql = "insert into reservation (name, date, time) values(?,?,?)";

        if (reservation.getId() != null) {
            throw new ReservationAlreadyExistsException("ID가 이미 존재하는 예약은 저장할 수 없습니다.");
        }
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(3, java.sql.Time.valueOf(reservation.getTime()));
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                long id = keys.getLong(1);
                return new Reservation(id,
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime());
            }
            throw new SQLException("자동 생성된 키가 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findById(Long id) {

        String sql = "select * from reservation where id = ?";


        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new NotFoundReservationException("해당 예약이 존재하지 않습니다.");
    }

    public void update(Long id, ReservationCommand reservationCommand) {

        String sql = "update reservation set name =?, date = ?, time = ? where id = ?";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, reservationCommand.name());
            preparedStatement.setDate(2, java.sql.Date.valueOf(reservationCommand.date()));
            preparedStatement.setTime(3, java.sql.Time.valueOf(reservationCommand.time()));
            preparedStatement.setLong(4, id);
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new NotFoundReservationException("해당 예약이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {

        String sql = "delete from reservation where id = ?";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new NotFoundReservationException("해당 예약이 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 드라이버 연결
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostConstruct
    void init() {
        String sql = "create table Reservation( id BIGINT AUTO_INCREMENT PRIMARY KEY, name varchar(50), date date, time time);";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
