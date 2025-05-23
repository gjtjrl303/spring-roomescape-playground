package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TimeDaoTest {

    @Autowired TimeDao timeDao;
    @Autowired private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void resetDatabase() {
        jdbcTemplate.execute("DELETE FROM reservation");
        jdbcTemplate.execute("DELETE FROM time");
        jdbcTemplate.execute("ALTER TABLE time ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void save() {
        //given
        Time time = new Time(LocalTime.parse("13:00"));

        //when
        Time save = timeDao.save(time);

        //then
        assertThat(save.getId()).isEqualTo(1L);
        assertThat(save.getTime()).isEqualTo("13:00");
    }

    @Test
    void findById() {
        //given
        Time time = new Time(LocalTime.parse("13:00"));
        Time save = timeDao.save(time);

        //when
        Time byId = timeDao.findById(save.getId()).get();


        //then
        assertThat(byId.getId()).isEqualTo(1L);
        assertThat(byId.getTime()).isEqualTo("13:00");
    }

    @Test
    void findByTime() {
        //given
        Time time = new Time(LocalTime.parse("13:00"));
        Time save = timeDao.save(time);

        //when
        Optional<Time> byTime = timeDao.findByTime(LocalTime.parse("13:00"));

        //then
        assertThat(byTime).isPresent();
        assertThat(byTime.get().getId()).isEqualTo(1L);
        assertThat(byTime.get().getTime()).isEqualTo("13:00");
    }

    @Test
    void findAll() {
        //given
        Time time1 = new Time(LocalTime.parse("13:00"));
        Time time2 = new Time(LocalTime.parse("14:00"));

        Time save1 = timeDao.save(time1);
        Time save2 = timeDao.save(time2);

        //when
        List<Time> all = timeDao.findAll();

        //then
        assertThat(all).size().isEqualTo(2);
    }
}
