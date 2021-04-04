package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.liquec.calculation.*;
import edu.uoc.geopocket.liquec.calculation.ranges.crr.*;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CycleResistanceRatioTask extends AbstractTaskExecutable {

    private CrrRangeFactory crrRangeFactory;

    private CrrRoadMapFactory crrRoadMapFactory;

    private EvaluableFinesFactory evaluableFinesFactory;

    @Autowired
    public CycleResistanceRatioTask(final CrrRangeFactory crrRangeFactory,
                                    final CrrRoadMapFactory crrRoadMapFactory,
                                    final EvaluableFinesFactory evaluableFinesFactory) {
        super(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO);
        this.crrRangeFactory = crrRangeFactory;
        this.crrRoadMapFactory = crrRoadMapFactory;
        this.evaluableFinesFactory = evaluableFinesFactory;
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {
        final int layerIndexWithSptInside = LiquecHelper
            .retrieveLayerIndexWithElementInside(liquecProject.getSoilLayers(), targetSpt.getDepth());
        log.info("Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_SPT_INSIDE_NOT_FOUND);
        }

        final Float finesContent = liquecProject.getSoilLayers().get(layerIndexWithSptInside).getFinesContent();
        log.info("Layer fines content: " + finesContent + " (%)");

        for (Crr crr : Crr.values()) {
            final boolean contains = this.crrRangeFactory.getCrrEvaluation(crr).contains(Double.valueOf(finesContent));
            log.info("EvaluableFines contains: " + contains);

            if (contains) {
                final Double cycleResistanceRatio =  crr.getBounds().length == 1
                    ? this.retrieveValue(crr, 0, targetSpt.getSptResult().getSptCorrected(), liquecProject.getCode())
                    : this.retrieveOffsetValue(crr, targetSpt.getSptResult().getSptCorrected(), (Double.valueOf(finesContent)), liquecProject.getCode());
                targetSpt.getSptResult().setCycleResistanceRatio(cycleResistanceRatio);
                break;
            }

        }
    }

    private Double retrieveValue(final Crr crr, final int index, final Double sptCorrected, final LiquecCode code) {
        double cycleResistanceRatio = 0.0;
        log.info("SPT Corrected: " + sptCorrected + " (N60)");
        for (Object fines : this.crrRoadMapFactory.getRoadMap(crr, code, index).getSteps()) {

            final boolean contains = this.evaluableFinesFactory.getRange((EvaluableFines) fines).contains(sptCorrected);
            log.info("SPT corrected contains: " + contains);

            if (contains) {
                cycleResistanceRatio = ((EvaluableFines) fines).getPolynomial().getValue(sptCorrected);
                log.info("CRR: " + cycleResistanceRatio);
                break;
            }

        }
        return cycleResistanceRatio;
    }

    private Double retrieveOffsetValue(final Crr crr, final Double sptCorrected, final Double finesContent, final LiquecCode code) {
        final Double crr1 = this.retrieveValue(crr, 0, sptCorrected, code);
        log.info("CRR first: " + crr1);

        final Double crr2 = this.retrieveValue(crr, 1, sptCorrected, code);
        log.info("CRR second: " + crr2);

        final Double cycleResistanceRatio = ((crr1 * (finesContent - crr.getBounds()[0].getBound()))
            + (crr2 * (crr.getBounds()[1].getBound() - finesContent))) / (crr.getBounds()[1].getBound() - crr.getBounds()[0].getBound());
        log.info("Final CRR: " + cycleResistanceRatio);

        return cycleResistanceRatio;
    }

}
