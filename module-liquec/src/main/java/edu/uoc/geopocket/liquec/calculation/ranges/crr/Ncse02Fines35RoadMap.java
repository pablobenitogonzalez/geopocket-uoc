package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.ranges.crr.EvaluableFines.*;

@Component
public class Ncse02Fines35RoadMap extends RoadMap<EvaluableFines> {
    public Ncse02Fines35RoadMap() {
        STEPS.add(NCSE02_F35_LESS_OR_EQUAL);
        STEPS.add(NCSE02_F35_BETWEEN);
        STEPS.add(NCSE02_F35_GREATER_OR_EQUAL);
    }
}
