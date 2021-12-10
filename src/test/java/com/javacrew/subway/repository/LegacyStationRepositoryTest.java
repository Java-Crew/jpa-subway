package com.javacrew.subway.repository;

import com.javacrew.subway.domain.LegacyStation;
import com.javacrew.subway.domain.Line;
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
@DisplayName("LegacyStationRepository 테스트")
class LegacyStationRepositoryTest {

    @Autowired
    private LegacyStationRepository legacyStationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Test
    void save() {
        LegacyStation expected = LegacyStation.builder()
                .name("잠실역")
                .build();
        LegacyStation actual = legacyStationRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName())
        );
    }

    @Test
    void findByName() {
        String expected = "잠실역";
        legacyStationRepository.save(LegacyStation.builder()
                .name(expected)
                .build());
        String actual = legacyStationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identity() {
        LegacyStation legacyStation1 = legacyStationRepository.save(LegacyStation.builder()
                .name("잠실역")
                .build());
        LegacyStation legacyStation2 = legacyStationRepository.findById(legacyStation1.getId()).get();
        assertThat(legacyStation1).isSameAs(legacyStation2);
    }

    @Test
    void update() {
        LegacyStation legacyStation1 = legacyStationRepository.save(LegacyStation.builder()
                .name("잠실역")
                .build());
        legacyStation1.changeName("몽촌토성역");
        LegacyStation legacyStation2 = legacyStationRepository.findById(legacyStation1.getId()).get();
        assertThat(legacyStation2).isNotNull();
    }

    @Test
    void saveWithLine() {
        LegacyStation expected = legacyStationRepository.save(LegacyStation.builder()
                .name("잠실역")
                .build());

        expected.changeLine(lineRepository.save(Line.builder()
                .name("2호선")
                .build()));

        LegacyStation actual = legacyStationRepository.save(expected);
        assertThat(actual).isSameAs(expected);
        legacyStationRepository.flush();
    }

    @Test
    void findByNameWithLine() {
        LegacyStation actual = legacyStationRepository.findByName("교대역");
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getLine().getName()).isEqualTo("3호선")
        );
    }

    @Test
    void updateWithLine() {
        LegacyStation expected = legacyStationRepository.findByName("교대역");
        expected.changeLine(lineRepository.save(Line.builder()
                .name("2호선")
                .build()));

        LegacyStation actual = legacyStationRepository.findById(expected.getId()).get();
        assertThat(actual).isSameAs(expected);
        legacyStationRepository.flush();
    }

    @Test
    void removeLine() {
        LegacyStation expected = legacyStationRepository.findByName("교대역");
        expected.changeLine(null);
        legacyStationRepository.flush();

        LegacyStation legacyStation = legacyStationRepository.findById(expected.getId()).get();
        assertThat(legacyStation.getLine()).isNull();
    }
}