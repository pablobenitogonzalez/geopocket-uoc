package edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines15;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurocodeGreaterOrEqual15 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected >= 24.5;
        log.info("Range greater or equals: " + contains);
        return contains;
    }

}
