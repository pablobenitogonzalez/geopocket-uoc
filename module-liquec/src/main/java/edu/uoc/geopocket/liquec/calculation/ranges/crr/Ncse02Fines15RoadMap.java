package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.ranges.crr.EvaluableFines.*;

@Component
public class Ncse02Fines15RoadMap extends RoadMap<EvaluableFines> {
    public Ncse02Fines15RoadMap() {
        STEPS.add(NCSE02_F15_LESS_OR_EQUAL);
        STEPS.add(NCSE02_F15_BETWEEN);
        STEPS.add(NCSE02_F15_GREATER_OR_EQUAL);
    }
}
