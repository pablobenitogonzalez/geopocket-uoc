package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.liquec.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.ranges.crr.EvaluableFines.*;

@Component
public class Ncse02Fines05RoadMap extends RoadMap<EvaluableFines> {
    public Ncse02Fines05RoadMap() {
        STEPS.add(NCSE02_F05_LESS_OR_EQUAL);
        STEPS.add(NCSE02_F05_BETWEEN);
        STEPS.add(NCSE02_F05_GREATER_OR_EQUAL);
    }
}
