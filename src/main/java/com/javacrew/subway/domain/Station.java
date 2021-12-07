package com.javacrew.subway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @Builder
    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeLine(Line line) {
        this.line = line;
    }
}
