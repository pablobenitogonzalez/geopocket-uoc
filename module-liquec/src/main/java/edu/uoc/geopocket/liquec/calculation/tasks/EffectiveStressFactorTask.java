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
public class EffectiveStressFactorTask extends AbstractTaskExecutable {

    public EffectiveStressFactorTask() {
        super(LiquecTask.CALCULATE_EFFECTIVE_STRESS_FACTOR);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        double effectiveStressFactor = Math.pow(100.0 / targetSpt.getSptResult().getEffectiveStress(), 0.5);

        if (LiquecCode.EUROCODE.equals(liquecProject.getCode())) {
            effectiveStressFactor = effectiveStressFactor < 0.5 ? 0.5 : effectiveStressFactor > 2.0 ? 2.0 : effectiveStressFactor;
        }

        log.info("Effective stress factor: " + effectiveStressFactor);

        targetSpt.getSptResult().setEffectiveStressFactor(effectiveStressFactor);
    }

}
