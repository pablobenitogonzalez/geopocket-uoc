package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.calculation.Describable;

public enum LiquecTask implements Describable {
    CHECK_DEPTH_ABOVE_GWT("Check Depth Above GWT"),
    CHECK_MAX_DEPTH("Check Max Depth"),
    CHECK_LIQUEFACTION_OPTION("Check Liquefaction Option"),
    CALCULATE_TOTAL_STRESS("Total Stress"),
    CALCULATE_INTERSTITIAL_STRESS("Interstitial Stress"),
    CALCULATE_EFFECTIVE_STRESS("Effective Stress"),
    CALCULATE_EFFECTIVE_STRESS_FACTOR("Effective Stress Factor"),
    CALCULATE_SPT_CORRECTION("SPT Correction"),
    CALCULATE_CYCLE_RESISTANCE_RATIO("Cycle Resistance Ratio (CRR)"),
    CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION("Coefficient Contribution Correction (KM)"),
    CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION("Earthquake Magnitude Correction (CM)"),
    CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION("Cycle Resistance Ratio (CRR) Correction"),
    CALCULATE_DEPTH_FACTOR("Depth Factor"),
    CALCULATE_CYCLE_STRESS_RATIO("Cycle Stress Ratio (CSR)"),
    CALCULATE_SAFETY_FACTOR("Safety Factor");

    private String description;

    LiquecTask(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
