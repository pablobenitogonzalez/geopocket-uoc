package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.RoadMap;
import org.springframework.stereotype.Component;

import static edu.uoc.geopocket.liquec.calculation.ranges.crr.EvaluableFines.*;

@Component
public class EurocodeFines15RoadMap extends RoadMap<EvaluableFines> {
    public EurocodeFines15RoadMap() {
        STEPS.add(EUROCODE_F15_LESS_OR_EQUAL);
        STEPS.add(EUROCODE_F15_BETWEEN_FIRST_SECTION);
        STEPS.add(EUROCODE_F15_BETWEEN_SECOND_SECTION);
        STEPS.add(EUROCODE_F15_GREATER_OR_EQUAL);
    }
}
