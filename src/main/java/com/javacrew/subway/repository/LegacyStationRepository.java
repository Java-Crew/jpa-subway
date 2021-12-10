package com.javacrew.subway.repository;

import com.javacrew.subway.domain.LegacyStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegacyStationRepository extends JpaRepository<LegacyStation, Long> {
    LegacyStation findByName(String name);
}
