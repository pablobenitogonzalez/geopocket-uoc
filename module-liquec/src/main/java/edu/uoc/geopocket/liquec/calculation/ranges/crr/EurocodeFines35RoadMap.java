package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.ranges.crr.EvaluableFines.*;

@Component
public class EurocodeFines35RoadMap extends RoadMap<EvaluableFines> {
    public EurocodeFines35RoadMap() {
        STEPS.add(EUROCODE_F35_LESS_OR_EQUAL);
        STEPS.add(EUROCODE_F35_BETWEEN_FIRST_SECTION);
        STEPS.add(EUROCODE_F35_BETWEEN_SECOND_SECTION);
        STEPS.add(EUROCODE_F35_GREATER_OR_EQUAL);
    }
}
