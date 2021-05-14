package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.*;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CheckLiquefactionOptionLiquecTask extends AbstractLiquecTaskExecutable {

    public CheckLiquefactionOptionLiquecTask() {
        super(LiquecTask.CHECK_LIQUEFACTION_OPTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {
        final int layerIndexWithSptInside = LiquecHelper
            .retrieveLayerIndexWithElementInside(liquec.getSoilLayers(), targetSpt.getDepth());
        log.info("Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_SPT_INSIDE_NOT_FOUND);
        }

        log.info("Check liquefaction: " + liquec.getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction());
        if (!liquec.getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction()) {
            throw new LiquecTaskException(task, Result.SKIP, LiquecReason.NOT_LIQUEFACTION);
        }
    }

}
