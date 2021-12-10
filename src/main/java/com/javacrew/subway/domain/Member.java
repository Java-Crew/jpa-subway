package com.javacrew.subway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Favorite> favorites;

    @Builder
    public Member(String name) {
        this.name = name;
        favorites = new ArrayList<>();
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }
}
