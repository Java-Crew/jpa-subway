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
    private List<Station> stations = new ArrayList<>();

    @Builder
    public Line(String name) {
        this.name = name;
        stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        this.stations.add(station);
        station.changeLine(this);
    }
}
