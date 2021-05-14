package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.calculation.Reason;

public enum LiquecReason implements Reason {
    MAX_DEPTH_CHECK("> %depth% (m)"),
    ABOVE_GWT("above GWT"),
    NOT_LIQUEFACTION("not check"),
    LAYER_WITH_GWT_INSIDE_NOT_FOUND("error GWT"),
    LAYER_WITH_SPT_INSIDE_NOT_FOUND("error SPT");

    private String message;

    LiquecReason(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
