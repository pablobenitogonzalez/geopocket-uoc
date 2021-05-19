package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.calculation.Polynomial;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class EarthquakeMagnitudeCorrectionLiquecTask extends AbstractLiquecTaskExecutable {

    public EarthquakeMagnitudeCorrectionLiquecTask() {
        super(LiquecTask.CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        final BigDecimal earthquakeMagnitudeCorrection =
                Polynomial.EARTHQUAKE_MAGNITUDE_CORRECTION.getValue(BigDecimal.valueOf(liquec.getEarthquakeMagnitude()));

        log.debug("Earthquake magnitude correction (CM): " + earthquakeMagnitudeCorrection);

        targetSpt.getSptResult().setEarthquakeMagnitudeCorrection(earthquakeMagnitudeCorrection);
    }

}
