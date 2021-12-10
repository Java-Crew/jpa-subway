package com.javacrew.subway.repository;

import com.javacrew.subway.domain.LineStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, Long> {
}
