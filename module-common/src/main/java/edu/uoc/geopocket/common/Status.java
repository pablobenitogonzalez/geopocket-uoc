package edu.uoc.geopocket.common;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {
    DRAFT ("draft"),
    CALCULATED("calculated");

    private String name;

    Status(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
