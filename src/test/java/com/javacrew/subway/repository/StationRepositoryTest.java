package com.javacrew.subway.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.javacrew.subway.domain.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Station expected = new Station("잠실역");
        Station actual = stationRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(new Station(expected));
        String actual = stationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        Station station1 = stationRepository.save(new Station("잠실역"));
        Station station2 = stationRepository.findByName(station1.getName());
        assertThat(station1 == station2).isTrue();
    }

    @Test
    void update() {
        //Identity 전략이므로 쓰기 지연 작동 X
        Station station1 = stationRepository.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        //JPQL이므로 flush 발생, 변경 감지가 일어나 station1 변경 내용이 데이터베이스에 반영됨
        Station station2 = stationRepository.findByName("몽촌토성역");
        assertThat(station2).isNotNull();
    }
}
