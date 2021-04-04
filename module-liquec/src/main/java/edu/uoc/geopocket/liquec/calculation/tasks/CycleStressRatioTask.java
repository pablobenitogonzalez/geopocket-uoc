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
public class CycleStressRatioTask extends AbstractTaskExecutable {

    public CycleStressRatioTask() {
        super(LiquecTask.CALCULATE_CYCLE_STRESS_RATIO);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        Double cycleStressRatio = 0.65 * (targetSpt.getSptResult().getTotalStress() / targetSpt.getSptResult().getEffectiveStress()) * liquecProject.getPeakGroundAcceleration();

        if (LiquecCode.NCSE_02.equals(liquecProject.getCode())) {
            cycleStressRatio = cycleStressRatio * targetSpt.getSptResult().getDepthFactor();
        }

        log.info("Cycle stress ratio (CSR): " + cycleStressRatio);

        targetSpt.getSptResult().setCycleStressRatio(cycleStressRatio);
    }

}
