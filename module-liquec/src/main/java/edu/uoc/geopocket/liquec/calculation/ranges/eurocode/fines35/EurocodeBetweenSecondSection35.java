package edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines35;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurocodeBetweenSecondSection35 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 17 && sptCorrected < 21;
        log.info("Range between second section: " + contains);
        return contains;
    }

}
