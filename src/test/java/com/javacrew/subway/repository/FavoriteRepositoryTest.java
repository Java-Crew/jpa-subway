package com.javacrew.subway.repository;

import com.javacrew.subway.domain.Favorite;
import com.javacrew.subway.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class FavoriteRepositoryTest {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FavoriteRepository favoriteRepository;

    @Test
    void save() {
        Member expected = new Member("jason");
        expected.addFavorite(favoriteRepository.save(new Favorite()));
        Member actual = memberRepository.save(expected);
        memberRepository.flush();
    }
}
