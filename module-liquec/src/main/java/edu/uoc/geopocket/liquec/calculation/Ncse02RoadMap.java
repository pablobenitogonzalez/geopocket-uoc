package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.LiquecTask.*;

@Component
public class Ncse02RoadMap extends RoadMap<LiquecTask> {
    public Ncse02RoadMap() {
        STEPS.add(CHECK_DEPTH_ABOVE_GWT);
        STEPS.add(CHECK_LIQUEFACTION_OPTION);
        STEPS.add(CALCULATE_TOTAL_STRESS);
        STEPS.add(CALCULATE_INTERSTITIAL_STRESS);
        STEPS.add(CALCULATE_EFFECTIVE_STRESS);
        STEPS.add(CALCULATE_EFFECTIVE_STRESS_FACTOR);
        STEPS.add(CALCULATE_SPT_CORRECTION);
        STEPS.add(CALCULATE_CYCLE_RESISTANCE_RATIO);
        STEPS.add(CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION);
        STEPS.add(CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION);
        STEPS.add(CALCULATE_DEPTH_FACTOR);
        STEPS.add(CALCULATE_CYCLE_STRESS_RATIO);
        STEPS.add(CALCULATE_SAFETY_FACTOR);
    }
}
