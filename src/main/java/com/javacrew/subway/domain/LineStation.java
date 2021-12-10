package com.javacrew.subway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @OneToOne(mappedBy = "lineStation")
    private Station station;

    @Builder
    public LineStation(Line line, Station station) {
        this.line = line;
        this.station = station;
    }
}
