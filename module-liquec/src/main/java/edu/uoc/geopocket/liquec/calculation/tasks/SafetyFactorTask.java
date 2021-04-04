package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SafetyFactorTask extends AbstractTaskExecutable {

    public SafetyFactorTask() {
        super(LiquecTask.CALCULATE_SAFETY_FACTOR);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final Double safetyFactor = targetSpt.getSptResult().getCycleResistanceRatioCorrected() / targetSpt.getSptResult().getCycleStressRatio();

        log.debug("Safety factor: " + safetyFactor);

        targetSpt.getSptResult().setSafetyFactor(safetyFactor);
    }

}
