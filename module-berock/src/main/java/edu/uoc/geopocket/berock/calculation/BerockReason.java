package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.common.calculation.Reason;

public enum BerockReason implements Reason {
    GENERIC("Generic error");

    private String message;

    BerockReason(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
