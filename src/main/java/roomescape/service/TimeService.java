package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.service.dto.TimeResult;
import roomescape.service.dto.TimeSaveCommand;

import java.util.List;
import java.util.Optional;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResult save(TimeSaveCommand timeSaveCommand) {
        Optional<Time> existing = timeDao.findByTime(timeSaveCommand.time());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("같은 시간은 한번만 등록할 수 있습니다");
        }
        Time saveTime = timeDao.save(timeSaveCommand.toEntity());
        return TimeResult.from(saveTime);
    }

    public List<TimeResult> findAll() {
        List<Time> times = timeDao.findAll();
        return times.stream()
                .map(TimeResult::from)
                .toList();
    }

    public void delete(Long id) {
        timeDao.delete(id);
    }


}
