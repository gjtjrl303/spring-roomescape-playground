package roomescape.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.service.TimeService;
import roomescape.service.dto.TimeResult;
import roomescape.service.dto.TimeSaveCommand;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeDaoConcurrencyTest {

    @Autowired
    private TimeService timeService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @BeforeEach
//    void setUp() {
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS time (" +
//                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
//                "start_time TIME " + // UNIQUE 제약 없는 경우를 가정
//                ")");
//    }

    @Test
    void duplicateTimeMayBeInsertedDueToConcurrency() throws InterruptedException {
        int threadCount = 2;
        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        AtomicReference<Long> id1 = new AtomicReference<>();
        AtomicReference<Long> id2 = new AtomicReference<>();

        TimeSaveCommand timeSaveCommand = new TimeSaveCommand(
                LocalTime.parse("10:00")
        );

        Runnable task = ( ) -> {
            readyLatch.countDown();
            try {
                startLatch.await();
                TimeResult saved = timeService.save(timeSaveCommand); // 내부적으로 find -> insert
                if (id1.get() == null) {
                    id1.set(saved.id());
                } else {
                    id2.set(saved.id());
                }
            } catch (InterruptedException ignored) {
            } finally {
                doneLatch.countDown();
            }
        };

        new Thread(task).start();
        new Thread(task).start();

        readyLatch.await();
        startLatch.countDown();
        doneLatch.await();

        System.out.println("Time ID from Thread 1: " + id1.get());
        System.out.println("Time ID from Thread 2: " + id2.get());

        assertThat(id1.get()).isNotEqualTo(id2.get())
                .withFailMessage("중복된 시간이 저장되어 서로 다른 ID를 가짐");
    }
}
