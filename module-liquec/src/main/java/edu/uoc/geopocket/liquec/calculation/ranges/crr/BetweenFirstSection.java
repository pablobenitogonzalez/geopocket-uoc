package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.liquec.calculation.LiquecFines;
import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BetweenFirstSection implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > LiquecFines.BOUND_05.getBound() && sptCorrected < LiquecFines.BOUND_15.getBound();
        log.info("Range between first section [" + LiquecFines.BOUND_05.getBound() + "-" + LiquecFines.BOUND_15.getBound() + "]: " + contains);
        return contains;
    }

}
