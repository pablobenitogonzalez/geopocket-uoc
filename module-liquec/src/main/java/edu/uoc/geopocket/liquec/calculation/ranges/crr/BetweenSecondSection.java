package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.liquec.calculation.LiquecFines;
import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BetweenSecondSection implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected >= LiquecFines.BOUND_15.getBound() && sptCorrected < LiquecFines.BOUND_35.getBound();
        log.info("Range between second section [" + LiquecFines.BOUND_15.getBound() + "-" + LiquecFines.BOUND_35.getBound() + "]: " + contains);
        return contains;
    }

}
