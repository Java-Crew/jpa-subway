package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);
}
