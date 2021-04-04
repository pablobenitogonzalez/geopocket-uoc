package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EffectiveStressTask extends AbstractTaskExecutable {

    public EffectiveStressTask() {
        super(LiquecTask.CALCULATE_EFFECTIVE_STRESS);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final double effectiveStress = targetSpt.getSptResult().getTotalStress() - targetSpt.getSptResult().getInterstitialStress();

        log.debug("Effective stress: " + effectiveStress + " KN/m2");

        targetSpt.getSptResult().setEffectiveStress(effectiveStress);
    }

}
