package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.liquec.entities.SoilLayer;

import java.util.ArrayList;
import java.util.List;

public final class LiquecHelper {

    private LiquecHelper() {}

    public static int retrieveLayerIndexWithGwtInside(
        final List<SoilLayer> soilLayers,
            final Float groundWaterTableDepth) {
        if (groundWaterTableDepth == 0) {
            return 0;
        }
        return retrieveLayerIndexWithElementInside(soilLayers, groundWaterTableDepth);
    }

    public static int retrieveLayerIndexWithElementInside(
            final List<SoilLayer> soilLayers,
            final Float depth) {
        int index = 0;
        int layerIndexWithElementInside = -1;
        for (SoilLayer soilLayer : soilLayers) {
            if (depth > soilLayer.getStartDepth() && depth <= soilLayer.getFinalDepth()) {
                layerIndexWithElementInside = index;
            }
            index++;
        }
        return layerIndexWithElementInside;
    }

    public static List<Integer> retrieveLayerIndexesAboveGwt(
            final List<SoilLayer> soilLayers,
            final Float groundWaterTableDepth,
            final int layerIndexWithSptInside) {
        int index = 0;
        List<Integer> layerIndexesAboveGwt = new ArrayList<>();
        for (SoilLayer soilLayer : soilLayers) {
            if (soilLayer.getStartDepth() < groundWaterTableDepth
                && soilLayer.getFinalDepth() <= groundWaterTableDepth
                && index != layerIndexWithSptInside) {
                layerIndexesAboveGwt.add(index);
            }
            index++;
        }
        return layerIndexesAboveGwt;
    }

    public static List<Integer> retrieveLayerIndexesBelowGwt(
            final List<SoilLayer> soilLayers,
            final Float groundWaterTableDepth,
            final int layerIndexWithSptInside,
            final Float sptDepth) {
        int index = 0;
        List<Integer> layerIndexesBelowGwt = new ArrayList<>();
        for (SoilLayer soilLayer : soilLayers) {
            if (soilLayer.getStartDepth() >= groundWaterTableDepth
                && soilLayer.getFinalDepth() > groundWaterTableDepth
                && index != layerIndexWithSptInside
                && soilLayer.getStartDepth() < sptDepth) {
                layerIndexesBelowGwt.add(index);
            }
            index++;
        }
        return layerIndexesBelowGwt;
    }

}
