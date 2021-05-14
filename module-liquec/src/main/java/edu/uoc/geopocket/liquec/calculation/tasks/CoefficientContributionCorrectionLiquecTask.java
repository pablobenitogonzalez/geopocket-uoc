package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoefficientContributionCorrectionLiquecTask extends AbstractLiquecTaskExecutable {

    public CoefficientContributionCorrectionLiquecTask() {
        super(LiquecTask.CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        final double coefficientContributionCorrection = 1.5 - (1.8 * Math.pow(liquec.getCoefficientOfContribution() - 1.0, 0.5));

        log.info("Coefficient contribution correction (KM): " + coefficientContributionCorrection);

        targetSpt.getSptResult().setCoefficientContributionCorrection(coefficientContributionCorrection);
    }

}
