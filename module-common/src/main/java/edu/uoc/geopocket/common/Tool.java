package edu.uoc.geopocket.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Tool {
    BEROCK("berock"),
    LIQUEC("liquec");

    private String id;

    Tool(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
