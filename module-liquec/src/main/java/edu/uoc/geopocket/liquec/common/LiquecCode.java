package edu.uoc.geopocket.liquec.common;

import lombok.Getter;

@Getter
public enum LiquecCode {
    EUROCODE("EUROCODE", 1.25f),
    NCSE_02("NCSE-02", 1.50f);

    private String description;
    private Float safetyFactor;

    LiquecCode(final String description, final Float safetyFactor) {
        this.description = description;
        this.safetyFactor = safetyFactor;
    }
}
