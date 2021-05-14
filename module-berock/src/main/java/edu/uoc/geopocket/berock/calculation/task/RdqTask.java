package edu.uoc.geopocket.berock.calculation.task;

import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.entities.Berock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class RdqTask extends AbstractBerockTaskExecutable {

    public RdqTask() {
        super(BerockTask.CALCULATE_RDQ);
    }

    public void execute(final Berock berock) {
        BigDecimal calculatedRDQ = BigDecimal.valueOf(115)
                .subtract(BigDecimal.valueOf(3.3).multiply(berock.getResult().getVolumetricJointCount()));
        final BigDecimal limit = BigDecimal.valueOf(100.0);
        if (calculatedRDQ.compareTo(limit) > 0) {
            log.warn(String.format("Calculated RDQ above 100: %s", calculatedRDQ));
            calculatedRDQ = limit;
        }
        berock.getResult().setCalculatedRDQ(calculatedRDQ);
        log.info(String.format("Calculated RDQ: %s", berock.getResult().getCalculatedRDQ()));
    }
}
