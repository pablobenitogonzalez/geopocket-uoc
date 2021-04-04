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
public class SptCorrectionTask extends AbstractTaskExecutable {

    public SptCorrectionTask() {
        super(LiquecTask.CALCULATE_SPT_CORRECTION);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        double sptCorrected = targetSpt.getSptBlowCounts() * (targetSpt.getEnergyRatio() / 60.0) * targetSpt.getSptResult().getEffectiveStressFactor();

        if (LiquecCode.EUROCODE.equals(liquecProject.getCode())) {
            sptCorrected = targetSpt.getDepth() < 3.0 ? 0.75 * sptCorrected : sptCorrected;
        }

        log.info("SPT Corrected: " + sptCorrected + " (N60)");

        targetSpt.getSptResult().setSptCorrected(sptCorrected);
    }

}
