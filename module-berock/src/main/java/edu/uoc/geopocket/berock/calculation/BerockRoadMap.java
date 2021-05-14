package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.berock.calculation.BerockTask.*;

@Component
public class BerockRoadMap extends RoadMap<BerockTask> {
    public BerockRoadMap() {
        STEPS.add(CALCULATE_ALPHA_3A);
        STEPS.add(CALCULATE_JV);
        STEPS.add(CALCULATE_RDQ);
        STEPS.add(CALCULATE_ALPHA_3B);
        STEPS.add(CALCULATE_FINAL_ALPHA_3);
        STEPS.add(CALCULATE_ALLOWABLE_BEARING_PRESSURE);
    }
}
