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
public class SptCorrectionLiquecTask extends AbstractLiquecTaskExecutable {

    public SptCorrectionLiquecTask() {
        super(LiquecTask.CALCULATE_SPT_CORRECTION);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        BigDecimal sptCorrected = BigDecimal.valueOf(targetSpt.getSptBlowCounts())
                .multiply(BigDecimal.valueOf(targetSpt.getEnergyRatio()).divide(BigDecimal.valueOf(60.0), MATH_CONTEXT))
                .multiply(targetSpt.getSptResult().getEffectiveStressFactor());

        if (LiquecCode.EUROCODE.equals(liquec.getCode())) {
            sptCorrected = targetSpt.getDepth() < 3.0 ? BigDecimal.valueOf(0.75).multiply(sptCorrected) : sptCorrected;
        }

        log.info("SPT Corrected: " + sptCorrected + " (N60)");

        targetSpt.getSptResult().setSptCorrected(sptCorrected);
    }

}
