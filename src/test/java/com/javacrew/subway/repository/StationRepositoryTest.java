package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Line;
import com.javacrew.subway.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
    해당 테스트는 test/resources/data.sql 이용하여 데이터 초기화를 하고 있음.
    예제를 위해서 사용하지만, 실제 프로젝트에는 데이터베이스 초기화 기술을 혼합하여 사용하는 것을 추천하지 않는다.
    스프링 부트 2.5 이후부터는 기본 값으로 지원 안하므로 application.yml 설정의 spring.jpa.defer-datasource-initialization 값 true 변경
 */
@DataJpaTest
@DisplayName("StationRepository 테스트")
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

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

    @Test
    void saveWithLine() {
        Station expected = stationRepository.save(Station.builder()
                .name("잠실역")
                .build());

        expected.changeLine(lineRepository.save(Line.builder()
                .name("2호선")
                .build()));

        Station actual = stationRepository.save(expected);
        assertThat(actual).isSameAs(expected);
        stationRepository.flush();
    }

    @Test
    void findByNameWithLine() {
        Station actual = stationRepository.findByName("교대역");
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getLine().getName()).isEqualTo("3호선")
        );
    }

    @Test
    void updateWithLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.changeLine(lineRepository.save(Line.builder()
                .name("2호선")
                .build()));

        Station actual = stationRepository.findById(expected.getId()).get();
        assertThat(actual).isSameAs(expected);
        stationRepository.flush();
    }

    @Test
    void removeLine() {
        Station expected = stationRepository.findByName("교대역");
        expected.changeLine(null);
        stationRepository.flush();

        Station station = stationRepository.findById(expected.getId()).get();
        assertThat(station.getLine()).isNull();
    }
}