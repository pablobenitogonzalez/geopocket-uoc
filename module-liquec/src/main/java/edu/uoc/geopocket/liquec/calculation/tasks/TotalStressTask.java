package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.*;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.SoilLayer;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class TotalStressTask extends AbstractTaskExecutable {

    public TotalStressTask() {
        super(LiquecTask.CALCULATE_TOTAL_STRESS);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final int layerIndexWithGwtInside = LiquecHelper.retrieveLayerIndexWithGwtInside(liquecProject.getSoilLayers(),
                liquecProject.getGroundWaterTableDepth());
        log.info("Layer index with GWT inside: " + layerIndexWithGwtInside);

        if (layerIndexWithGwtInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_GWT_INSIDE_NOT_FOUND);
        }

        final int layerIndexWithSptInside = LiquecHelper
            .retrieveLayerIndexWithElementInside(liquecProject.getSoilLayers(), targetSpt.getDepth());
        log.info("Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_SPT_INSIDE_NOT_FOUND);
        }

        final List<Integer> layerIndexesAboveGwt = LiquecHelper.retrieveLayerIndexesAboveGwt(liquecProject.getSoilLayers(),
                liquecProject.getGroundWaterTableDepth(), layerIndexWithSptInside);
        log.info("Layer indexes above GWT: " + Arrays.toString(layerIndexesAboveGwt.toArray()));

        final List<Integer> layerIndexesBelowGwt = LiquecHelper.retrieveLayerIndexesBelowGwt(liquecProject.getSoilLayers(),
                liquecProject.getGroundWaterTableDepth(), layerIndexWithSptInside, targetSpt.getDepth());
        log.info("Layer indexes below GWT: " + Arrays.toString(layerIndexesBelowGwt.toArray()));

        double totalStress = this.retrieveStressFromLayersAboveGwt(liquecProject.getSoilLayers(), layerIndexesAboveGwt);

        totalStress += this.retrieveAboveStressFromLayerWithGwtInside(liquecProject.getSoilLayers().get(layerIndexWithGwtInside),
                liquecProject.getGroundWaterTableDepth());

        if (layerIndexWithGwtInside == layerIndexWithSptInside) {

            totalStress += this.retrieveBelowStressFromLayerWithGwtAndSptInside(liquecProject.getSoilLayers().get(layerIndexWithGwtInside),
                    liquecProject.getGroundWaterTableDepth(),  targetSpt.getDepth());

        } else {

            totalStress += this.retrieveBelowStressFromLayerWithGwtInside(liquecProject.getSoilLayers().get(layerIndexWithGwtInside),
                    liquecProject.getGroundWaterTableDepth());

            totalStress += this.retrieveStressFromLayersBelowGwt(liquecProject.getSoilLayers(), layerIndexesBelowGwt);

            totalStress += this.retrieveBelowStressFromLayerWithSptInside(liquecProject.getSoilLayers().get(layerIndexWithSptInside),
                    targetSpt.getDepth());
        }

        log.info("Total stress:" + totalStress + " KN/m2");

        targetSpt.getSptResult().setTotalStress(totalStress);
    }

    private double retrieveStressFromLayersAboveGwt(final List<SoilLayer> soilLayers, final List<Integer> layerIndexesAboveGwt) {
        double stress = 0;
        for (Integer index : layerIndexesAboveGwt) {
            stress += soilLayers.get(index).getAboveGwt() * (soilLayers.get(index).getFinalDepth() - soilLayers.get(index).getStartDepth());
        }
        log.info("Stress from layers above GWT: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveAboveStressFromLayerWithGwtInside(final SoilLayer soilLayer, final Float groundWaterTableDepth) {
        final double stress = soilLayer.getAboveGwt() * (groundWaterTableDepth - soilLayer.getStartDepth());
        log.info("Above stress from layer with GWT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithGwtAndSptInside(final SoilLayer soilLayer, final Float groundWaterTableDepth, final Float sptDepth) {
        final double stress = soilLayer.getBelowGwt() * (sptDepth - groundWaterTableDepth);
        log.info("Below stress from layer with GWT and SPT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithGwtInside(final SoilLayer soilLayer, final Float groundWaterTableDepth) {
        final double stress = soilLayer.getBelowGwt() * (soilLayer.getFinalDepth() - groundWaterTableDepth);
        log.info("Below stress from layer with GWT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveStressFromLayersBelowGwt(final List<SoilLayer> soilLayers, final List<Integer> layerIndexesBelowGwt) {
        double stress = 0;
        for (Integer index : layerIndexesBelowGwt) {
            stress += soilLayers.get(index).getBelowGwt() * (soilLayers.get(index).getFinalDepth() - soilLayers.get(index).getStartDepth());
        }
        log.info("Stress from layers below GWT: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithSptInside(final SoilLayer soilLayer, final Float sptDepth) {
        final double stress = soilLayer.getBelowGwt() * (sptDepth - soilLayer.getStartDepth());
        log.info("Below stress from layer with SPT inside: " + stress + " KN/m2");
        return stress;
    }
}
