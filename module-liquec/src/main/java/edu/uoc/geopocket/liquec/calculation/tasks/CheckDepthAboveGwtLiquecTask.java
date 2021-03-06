package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecReason;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.calculation.LiquecTaskException;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckDepthAboveGwtLiquecTask extends AbstractLiquecTaskExecutable {

    public CheckDepthAboveGwtLiquecTask() {
        super(LiquecTask.CHECK_DEPTH_ABOVE_GWT);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {
        if (targetSpt.getDepth() < liquec.getGroundWaterTableDepth()) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.ABOVE_GWT);
        }
    }
}
