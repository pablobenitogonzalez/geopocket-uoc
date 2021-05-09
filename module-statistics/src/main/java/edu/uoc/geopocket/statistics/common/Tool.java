package edu.uoc.geopocket.statistics.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Tool {
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
