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
@DisplayName("LineRepository 테스트")
class LineRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void findByName() {
        Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void not_save() {
        Line expected = Line.builder()
                .name("2호선")
                .build();

        expected.addStation(Station.builder()
                .name("잠실역")
                .build());

        lineRepository.save(expected);
        lineRepository.flush();

        Station actual = stationRepository.findByName("잠실역");
        assertThat(actual).isNull();
    }

    @Test
    void save() {
        Line expected = Line.builder()
                .name("2호선")
                .build();

        expected.addStation(stationRepository.save(Station.builder()
                .name("잠실역")
                .build()));

        lineRepository.save(expected);
        lineRepository.flush();

        Station actual = stationRepository.findByName("잠실역");
        assertThat(actual).isSameAs(expected.getStations().get(0));
    }
}