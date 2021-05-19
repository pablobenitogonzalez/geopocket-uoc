package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractLiquecTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class EffectiveStressLiquecTask extends AbstractLiquecTaskExecutable {

    public EffectiveStressLiquecTask() {
        super(LiquecTask.CALCULATE_EFFECTIVE_STRESS);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        final BigDecimal effectiveStress = targetSpt.getSptResult().getTotalStress().subtract(targetSpt.getSptResult().getInterstitialStress());

        log.debug("Effective stress: " + effectiveStress + " KN/m2");

        targetSpt.getSptResult().setEffectiveStress(effectiveStress);
    }

}
