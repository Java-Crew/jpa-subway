package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("StationRepository 테스트")
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Test
    void save() {
        Station expected = Station.builder()
                .name("잠실역")
                .build();
        Station actual = stationRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        stationRepository.save(Station.builder()
                .name(expected)
                .build());
        String actual = stationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        Station station1 = stationRepository.save(Station.builder()
                .name("잠실역")
                .build());
        Station station2 = stationRepository.findById(station1.getId()).get();
        assertThat(station1).isSameAs(station2);
    }

    @Test
    void update() {
        Station station1 = stationRepository.save(Station.builder()
                .name("잠실역")
                .build());
        station1.changeName("몽촌토성역");
        Station station2 = stationRepository.findById(station1.getId()).get();
        assertThat(station2).isNotNull();
    }
}