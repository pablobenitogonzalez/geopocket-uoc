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
public class InterstitialStressLiquecTask extends AbstractLiquecTaskExecutable {

    public InterstitialStressLiquecTask() {
        super(LiquecTask.CALCULATE_INTERSTITIAL_STRESS);
    }

    public void execute(final Liquec liquec, final Spt targetSpt) {

        final BigDecimal interstitialStress = BigDecimal.valueOf(10.0)
                .multiply(BigDecimal.valueOf(targetSpt.getDepth()).subtract(BigDecimal.valueOf(liquec.getGroundWaterTableDepth())));

        log.info("Interstitial stress: " + interstitialStress + " KN/m2");

        targetSpt.getSptResult().setInterstitialStress(interstitialStress);
    }

}
