package edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines15;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Ncse02LessOrEqual15 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected <= 5;
        log.info("Range less or equals: " + contains);
        return contains;
    }

}
