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

import java.math.BigDecimal;

@Slf4j
@Component
public class CycleResistanceRatioLiquecTask extends AbstractLiquecTaskExecutable {

    private CrrRangeFactory crrRangeFactory;

    private CrrRoadMapFactory crrRoadMapFactory;

    private EvaluableFinesFactory evaluableFinesFactory;

    @Autowired
    public CycleResistanceRatioLiquecTask(final CrrRangeFactory crrRangeFactory,
                                          final CrrRoadMapFactory crrRoadMapFactory,
                                          final EvaluableFinesFactory evaluableFinesFactory) {
        super(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO);
        this.crrRangeFactory = crrRangeFactory;
        this.crrRoadMapFactory = crrRoadMapFactory;
        this.evaluableFinesFactory = evaluableFinesFactory;
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {
        final int layerIndexWithSptInside = LiquecHelper
            .retrieveLayerIndexWithElementInside(liquec.getSoilLayers(), targetSpt.getDepth());
        log.info("Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquecTaskException(task, Result.ERROR, LiquecReason.LAYER_WITH_SPT_INSIDE_NOT_FOUND);
        }

        final BigDecimal finesContent = BigDecimal.valueOf(liquec.getSoilLayers().get(layerIndexWithSptInside).getFinesContent());
        log.info("Layer fines content: " + finesContent + " (%)");

        for (Crr crr : Crr.values()) {
            final boolean contains = this.crrRangeFactory.getCrrEvaluation(crr).contains(finesContent.doubleValue());
            log.info("EvaluableFines contains: " + contains);

            if (contains) {
                final BigDecimal cycleResistanceRatio =  crr.getBounds().length == 1
                    ? this.retrieveValue(crr, 0, targetSpt.getSptResult().getSptCorrected(), liquec.getCode())
                    : this.retrieveOffsetValue(crr, targetSpt.getSptResult().getSptCorrected(), finesContent, liquec.getCode());
                targetSpt.getSptResult().setCycleResistanceRatio(cycleResistanceRatio);
                break;
            }

        }
    }

    private BigDecimal retrieveValue(final Crr crr, final int index, final BigDecimal sptCorrected, final LiquecCode code) {
        BigDecimal cycleResistanceRatio = BigDecimal.valueOf(0.0);
        log.info("SPT Corrected: " + sptCorrected + " (N60)");
        for (Object fines : this.crrRoadMapFactory.getRoadMap(crr, code, index).getSteps()) {

            final boolean contains = this.evaluableFinesFactory.getRange((EvaluableFines) fines).contains(sptCorrected.doubleValue());
            log.info("SPT corrected contains: " + contains);

            if (contains) {
                cycleResistanceRatio = ((EvaluableFines) fines).getPolynomial().getValue(sptCorrected);
                log.info("CRR: " + cycleResistanceRatio);
                break;
            }

        }
        return cycleResistanceRatio;
    }

    private BigDecimal retrieveOffsetValue(final Crr crr, final BigDecimal sptCorrected, final BigDecimal finesContent, final LiquecCode code) {
        final BigDecimal crr1 = this.retrieveValue(crr, 0, sptCorrected, code);
        log.info("CRR first: " + crr1);

        final BigDecimal crr2 = this.retrieveValue(crr, 1, sptCorrected, code);
        log.info("CRR second: " + crr2);

        final BigDecimal cycleResistanceRatio = ((crr1.multiply(finesContent.subtract(BigDecimal.valueOf(crr.getBounds()[0].getBound()))))
                .add(crr2.multiply(BigDecimal.valueOf(crr.getBounds()[1].getBound()).subtract(finesContent))))
                .divide(BigDecimal.valueOf(crr.getBounds()[1].getBound()).subtract(BigDecimal.valueOf(crr.getBounds()[0].getBound())), MATH_CONTEXT);
        log.info("Final CRR: " + cycleResistanceRatio);

        return cycleResistanceRatio;
    }

}
