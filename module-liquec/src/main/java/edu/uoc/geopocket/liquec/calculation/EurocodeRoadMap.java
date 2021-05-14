package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.LiquecTask.*;

@Component
public class EurocodeRoadMap extends RoadMap<LiquecTask> {
    public EurocodeRoadMap() {
        STEPS.add(CHECK_DEPTH_ABOVE_GWT);
        STEPS.add(CHECK_MAX_DEPTH);
        STEPS.add(CHECK_LIQUEFACTION_OPTION);
        STEPS.add(CALCULATE_TOTAL_STRESS);
        STEPS.add(CALCULATE_INTERSTITIAL_STRESS);
        STEPS.add(CALCULATE_EFFECTIVE_STRESS);
        STEPS.add(CALCULATE_EFFECTIVE_STRESS_FACTOR);
        STEPS.add(CALCULATE_SPT_CORRECTION);
        STEPS.add(CALCULATE_CYCLE_RESISTANCE_RATIO);
        STEPS.add(CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION);
        STEPS.add(CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION);
        STEPS.add(CALCULATE_CYCLE_STRESS_RATIO);
        STEPS.add(CALCULATE_SAFETY_FACTOR);
    }
}
