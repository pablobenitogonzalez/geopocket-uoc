package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CycleResistanceRatioCorrectionTask extends AbstractTaskExecutable {

    public CycleResistanceRatioCorrectionTask() {
        super(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        double cycleResistanceRatioCorrection;

        if (LiquecCode.EUROCODE.equals(liquecProject.getCode())) {
            cycleResistanceRatioCorrection = targetSpt.getSptResult().getCycleResistanceRatio() * targetSpt.getSptResult().getEarthquakeMagnitudeCorrection();
        } else {
            cycleResistanceRatioCorrection = targetSpt.getSptResult().getCycleResistanceRatio() * targetSpt.getSptResult().getCoefficientContributionCorrection();
        }

        log.info("Cycle resistance ratio correction: " + cycleResistanceRatioCorrection);

        targetSpt.getSptResult().setCycleResistanceRatioCorrected(cycleResistanceRatioCorrection);
    }

}
