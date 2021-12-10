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
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "line")
    private List<LegacyStation> legacyStations = new ArrayList<>();

    @Builder
    public Line(String name) {
        this.name = name;
        legacyStations = new ArrayList<>();
    }

    public void addStation(LegacyStation legacyStation) {
        this.legacyStations.add(legacyStation);

        if (legacyStation.getLine() != this) {
            legacyStation.changeLine(this);
        }
    }
}
