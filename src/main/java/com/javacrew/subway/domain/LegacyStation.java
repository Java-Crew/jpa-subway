package com.javacrew.subway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegacyStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @Builder
    public LegacyStation(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeLine(Line line) {
        if (!Objects.isNull(this.line)) {
            this.line.getLegacyStations().remove(this);
        }
        this.line = line;
        if (!line.getLegacyStations().contains(this)) {
            line.addStation(this);
        }
    }
}
