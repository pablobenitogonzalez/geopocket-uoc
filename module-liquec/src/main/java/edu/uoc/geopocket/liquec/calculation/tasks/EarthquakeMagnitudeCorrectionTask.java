package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.calculation.Polynomial;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EarthquakeMagnitudeCorrectionTask extends AbstractTaskExecutable {

    public EarthquakeMagnitudeCorrectionTask() {
        super(LiquecTask.CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final Double earthquakeMagnitudeCorrection = Polynomial.EARTHQUAKE_MAGNITUDE_CORRECTION.getValue(liquecProject.getEarthquakeMagnitude());

        log.debug("Earthquake magnitude correction (CM): " + earthquakeMagnitudeCorrection);

        targetSpt.getSptResult().setEarthquakeMagnitudeCorrection(earthquakeMagnitudeCorrection);
    }

}
