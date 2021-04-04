package edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines35;

import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Ncse02Between35 implements LiquecRange {

    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 2.82 && sptCorrected < 20.7;
        log.info("Range between first section: " + contains);
        return contains;
    }

}
