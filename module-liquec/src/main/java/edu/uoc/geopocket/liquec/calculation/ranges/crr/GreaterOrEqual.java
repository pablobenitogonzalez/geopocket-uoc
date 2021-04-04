package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.liquec.calculation.LiquecFines;
import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GreaterOrEqual implements LiquecRange {

    public boolean contains(final Double finesContent) {
        final boolean contains = finesContent >= LiquecFines.BOUND_35.getBound();
        log.info("Range greater or equals [" + LiquecFines.BOUND_35.getBound() + "]: " + contains);
        return contains;
    }

}
