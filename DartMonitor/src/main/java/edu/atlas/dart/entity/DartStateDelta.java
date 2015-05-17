package edu.atlas.dart.entity;

import lombok.Getter;

/**
 * @author GromHoll
 * @since 17.05.2015
 */
public class DartStateDelta {

    @Getter
    private final DartState state;

    @Getter
    private final double delta;

    public DartStateDelta(DartState state, double delta) {
        this.state = state;
        this.delta = delta;
        this.state.setDartStateDelta(this);
    }

}
