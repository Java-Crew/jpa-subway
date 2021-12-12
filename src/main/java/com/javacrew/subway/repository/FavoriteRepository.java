package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
