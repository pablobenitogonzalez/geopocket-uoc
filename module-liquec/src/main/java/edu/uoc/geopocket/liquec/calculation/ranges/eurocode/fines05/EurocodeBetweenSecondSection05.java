package edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurocodeBetweenSecondSection05 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 24.9 && sptCorrected < 30;
        log.info("Range between second section: " + contains);
        return contains;
    }

}
