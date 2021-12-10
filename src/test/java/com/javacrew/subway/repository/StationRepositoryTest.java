package com.javacrew.subway.repository;

import com.javacrew.subway.domain.LineStation;
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
    private LineStationRepository lineStationRepository;

    @Test
    void saveWithLineStation() {
        LineStation lineStation = lineStationRepository.save(LineStation.builder().build());
        Station expected = Station.builder()
                .name("잠실역")
                .lineStation(lineStation)
                .build();
        Station actual = stationRepository.save(expected);
        assertThat(actual).isSameAs(expected);
    }
}