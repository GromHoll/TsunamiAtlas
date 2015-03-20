package edu.atlas.dart.entity;


import lombok.*;

import java.util.Calendar;

public class DartState {

    @Getter
    private final Calendar date;

    @Getter
    private final double height;

    @Getter @Setter
    private double clearedHeightDelta = 0;

    public DartState(@NonNull Calendar date, double height) {
        this.date = date;
        this.height = height;
    }

    public double getClearedHeight() {
        return height - clearedHeightDelta;
    }

}
