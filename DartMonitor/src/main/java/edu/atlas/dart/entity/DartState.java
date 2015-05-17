package edu.atlas.dart.entity;


import lombok.Getter;
import lombok.NonNull;

import java.util.Calendar;
import java.util.Optional;

public class DartState {

    @Getter
    private final Calendar date;

    @Getter
    private final double height;

    private Optional<DartStateDelta> dartStateDelta;

    public DartState(@NonNull Calendar date, double height) {
        this.date = date;
        this.height = height;
        this.dartStateDelta = Optional.empty();
    }

    public double getClearedHeight() {
        return dartStateDelta.isPresent() ? height - dartStateDelta.get().getDelta() : Double.NaN;
    }

    public void setDartStateDelta(DartStateDelta dartStateDelta) {
        this.dartStateDelta = Optional.ofNullable(dartStateDelta);
    }

    public DartStateDelta getDartStateDelta() {
        return dartStateDelta.orElse(null);
    }

    public double getDelta() {
        return dartStateDelta.isPresent() ? dartStateDelta.get().getDelta() : Double.NaN;
    }

}
