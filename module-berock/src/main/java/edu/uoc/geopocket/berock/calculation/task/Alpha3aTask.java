package edu.uoc.geopocket.berock.calculation.task;

import ch.obermuhlner.math.big.BigDecimalMath;
import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockReason;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.calculation.BerockTaskException;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.entities.Joint;
import edu.uoc.geopocket.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;

@Slf4j
@Component
public class Alpha3aTask extends AbstractBerockTaskExecutable {

    public Alpha3aTask() {
        super(BerockTask.CALCULATE_ALPHA_3A);
    }

    public void execute(final Berock berock) {
        final Joint minJoint = berock.getJoints().stream().min(Comparator.comparingDouble(Joint::getSpacing))
                .orElseThrow(() -> new BerockTaskException(task, Result.ERROR, BerockReason.GENERIC));
        log.info(String.format("Minimum joint (m): %s", minJoint.getSpacing()));
        berock.getResult().setAlpha3a(BigDecimalMath.sqrt(BigDecimal.valueOf(minJoint.getSpacing()), MATH_CONTEXT));
        log.info(String.format("Alpha 3a: %s", berock.getResult().getAlpha3a()));
    }
}
