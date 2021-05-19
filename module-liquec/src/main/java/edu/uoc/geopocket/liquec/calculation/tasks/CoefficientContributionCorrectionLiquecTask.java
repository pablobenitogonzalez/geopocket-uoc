package edu.uoc.geopocket.liquec.calculation.tasks;

import ch.obermuhlner.math.big.BigDecimalMath;
import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class CoefficientContributionCorrectionLiquecTask extends AbstractLiquecTaskExecutable {

    public CoefficientContributionCorrectionLiquecTask() {
        super(LiquecTask.CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        final BigDecimal coefficientContributionCorrection =
                BigDecimal.valueOf(1.5).subtract(BigDecimal.valueOf(1.8).multiply(
                        BigDecimalMath.sqrt(BigDecimal.valueOf(liquec.getCoefficientOfContribution())
                                .subtract(BigDecimal.valueOf(1.0)),  MATH_CONTEXT)));

        log.info("Coefficient contribution correction (KM): " + coefficientContributionCorrection);

        targetSpt.getSptResult().setCoefficientContributionCorrection(coefficientContributionCorrection);
    }

}
