package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {

    void deleteByName(String name);
}
