package edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines05;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Ncse02Between05 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 5.5 && sptCorrected < 30;
        log.info("Range between first section: " + contains);
        return contains;
    }

}
