package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class CycleResistanceRatioCorrectionLiquecTask extends AbstractLiquecTaskExecutable {

    public CycleResistanceRatioCorrectionLiquecTask() {
        super(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        BigDecimal cycleResistanceRatioCorrection;

        if (LiquecCode.EUROCODE.equals(liquec.getCode())) {
            cycleResistanceRatioCorrection = targetSpt.getSptResult().getCycleResistanceRatio()
                    .multiply(targetSpt.getSptResult().getEarthquakeMagnitudeCorrection());
        } else {
            cycleResistanceRatioCorrection = targetSpt.getSptResult().getCycleResistanceRatio()
                    .multiply(targetSpt.getSptResult().getCoefficientContributionCorrection());
        }

        log.info("Cycle resistance ratio correction: " + cycleResistanceRatioCorrection);

        targetSpt.getSptResult().setCycleResistanceRatioCorrected(cycleResistanceRatioCorrection);
    }

}
