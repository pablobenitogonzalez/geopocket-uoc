package edu.uoc.geopocket.liquec.calculation.tasks;

import ch.obermuhlner.math.big.BigDecimalMath;
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
public class EffectiveStressFactorLiquecTask extends AbstractLiquecTaskExecutable {

    public EffectiveStressFactorLiquecTask() {
        super(LiquecTask.CALCULATE_EFFECTIVE_STRESS_FACTOR);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        BigDecimal effectiveStressFactor = BigDecimalMath.sqrt(BigDecimal.valueOf(100.0)
                .divide(targetSpt.getSptResult().getEffectiveStress(), MATH_CONTEXT), MATH_CONTEXT);

        if (LiquecCode.EUROCODE.equals(liquec.getCode())) {
            effectiveStressFactor = effectiveStressFactor.doubleValue() < 0.5 ?
                    BigDecimal.valueOf(0.5) : effectiveStressFactor.doubleValue() > 2.0 ?
                    BigDecimal.valueOf(2.0) : effectiveStressFactor;
        }

        log.info("Effective stress factor: " + effectiveStressFactor);

        targetSpt.getSptResult().setEffectiveStressFactor(effectiveStressFactor);
    }

}
