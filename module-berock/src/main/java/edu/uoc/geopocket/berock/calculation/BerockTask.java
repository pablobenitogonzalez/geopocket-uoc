package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.common.calculation.Describable;

public enum BerockTask implements Describable {
    CALCULATE_ALPHA_3A("Alpha 3a"),
    CALCULATE_ALPHA_3B("Alpha 3b"),
    CALCULATE_JV("Jv (Volumetric Joint Count)"),
    CALCULATE_RDQ("RDQ (Rock Quality Designation)"),
    CALCULATE_FINAL_ALPHA_3("Final Alpha 3"),
    CALCULATE_ALLOWABLE_BEARING_PRESSURE("Allowable Bearing Pressure");

    private final String description;

    BerockTask(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
