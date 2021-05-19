package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class CycleStressRatioLiquecTask extends AbstractLiquecTaskExecutable {

    public CycleStressRatioLiquecTask() {
        super(LiquecTask.CALCULATE_CYCLE_STRESS_RATIO);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        BigDecimal cycleStressRatio = BigDecimal.valueOf(0.65)
                .multiply(targetSpt.getSptResult().getTotalStress().divide(targetSpt.getSptResult().getEffectiveStress(), MATH_CONTEXT))
                .multiply(BigDecimal.valueOf(liquec.getPeakGroundAcceleration()));

        if (LiquecCode.NCSE_02.equals(liquec.getCode())) {
            cycleStressRatio = cycleStressRatio.multiply(targetSpt.getSptResult().getDepthFactor());
        }

        log.info("Cycle stress ratio (CSR): " + cycleStressRatio);

        targetSpt.getSptResult().setCycleStressRatio(cycleStressRatio);
    }

}
