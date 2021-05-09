package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.*;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckLiquefactionOptionTask extends AbstractTaskExecutable {

    public CheckLiquefactionOptionTask() {
        super(LiquecTask.CHECK_LIQUEFACTION_OPTION);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {
        final int layerIndexWithSptInside = LiquecHelper
            .retrieveLayerIndexWithElementInside(liquecProject.getSoilLayers(), targetSpt.getDepth());
        log.info("Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_SPT_INSIDE_NOT_FOUND);
        }

        log.info("Check liquefaction: " + liquecProject.getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction());
        if (!liquecProject.getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction()) {
            throw new LiquecTaskException(task, Result.SKIP, LiquecReason.NOT_LIQUEFACTION);
        }
    }

}
