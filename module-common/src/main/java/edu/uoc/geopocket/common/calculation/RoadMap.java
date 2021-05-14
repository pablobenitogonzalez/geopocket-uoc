package edu.uoc.geopocket.common.calculation;

import java.util.ArrayList;
import java.util.List;

public abstract class RoadMap<E> {
    protected List<E> STEPS = new ArrayList<>();
    public List<E> getSteps() {
        return STEPS;
    }
}
