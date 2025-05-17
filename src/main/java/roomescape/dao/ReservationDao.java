package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(Reservation reservation) {
        SqlParameterSource param =new BeanPropertySqlParameterSource(reservation);
        Number key = jdbcInsert.executeAndReturnKey(param);
        return new Reservation(
                key.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public Reservation findById(Long id) {
        try {
            String sql = "select * from reservation where id = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    reservationRowMapper(),
                    id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundReservationException("해당 id의 예약이 존재하지 않습니다. id = " + id);
        }
    }

    public void update(Long id, ReservationCommand reservationCommand) {
        String sql = "update reservation set name = ?, date = ?, time = ? where id = ?";
        jdbcTemplate.update(
                sql,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime(),
                id
        );
    }

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(
                sql,
                reservationRowMapper()
        );
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        int update = jdbcTemplate.update(sql, id);

        if (update == 0) {
            throw new NotFoundReservationException("삭제할 예약이 없습니다. id = " + id);
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    }
}
