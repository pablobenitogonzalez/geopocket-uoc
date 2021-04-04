package edu.uoc.geopocket.liquec.calculation;

public enum LiquecFines {
    BOUND_05(5.0),
    BOUND_15(15.0),
    BOUND_35(35.0);

    private double bound;

    LiquecFines(final double bound) {
        this.bound = bound;
    }

    public double getBound() {
        return bound;
    }
}
