package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepthFactorTask extends AbstractTaskExecutable {

    public DepthFactorTask() {
        super(LiquecTask.CALCULATE_DEPTH_FACTOR);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final Double depthFactor = 1.0 - (0.015 * targetSpt.getDepth());

        log.info("Depth factor (rd): " + depthFactor);

        targetSpt.getSptResult().setDepthFactor(depthFactor);
    }

}
