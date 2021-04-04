package edu.uoc.geopocket.liquec.calculation.tasks;

import edu.uoc.geopocket.liquec.calculation.AbstractTaskExecutable;
import edu.uoc.geopocket.liquec.calculation.LiquecTask;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.Spt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InterstitialStressTask extends AbstractTaskExecutable {

    public InterstitialStressTask() {
        super(LiquecTask.CALCULATE_INTERSTITIAL_STRESS);
    }

    public void execute(final Liquec liquecProject, final Spt targetSpt) {

        final double interstitialStress = 10.0 * (targetSpt.getDepth() - liquecProject.getGroundWaterTableDepth());

        log.info("Interstitial stress: " + interstitialStress + " KN/m2");

        targetSpt.getSptResult().setInterstitialStress(interstitialStress);
    }

}
