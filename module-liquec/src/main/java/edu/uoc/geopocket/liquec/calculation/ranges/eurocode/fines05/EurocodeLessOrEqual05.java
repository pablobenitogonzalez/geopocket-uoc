package edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurocodeLessOrEqual05 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected <= 4.2;
        log.info("Range less or equals: " + contains);
        return contains;
    }

}
