package com.javacrew.subway.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.javacrew.subway.domain.Line;
import com.javacrew.subway.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class LineRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void saveWithLine() {
        Station expected = new Station("잠실역");
        expected.setLine(lineRepository.save(new Line("2호선")));
        Station actual = stationRepository.save(expected);
        stationRepository.flush();
    }

    @Test
    void selectWithLine() {
        Station actual = stationRepository.findByName("교대역");
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> assertThat(actual.getLine().getName()).isEqualTo("3호선")
        );
    }

    @Test
    void updateWithLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(lineRepository.save(new Line("2호선")));
        stationRepository.flush();
    }

    @Test
    void removeLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.setLine(null);
        lineRepository.deleteByName("3호선");
        stationRepository.flush();
    }

    @Test
    void findByName() {
        Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void save() {
        Line expected = new Line("2호선");
        //expected.addStation(new Station("잠실역"));
        expected.addStation(stationRepository.save(new Station("잠실역")));
        lineRepository.save(expected);
        lineRepository.flush();
    }
}
