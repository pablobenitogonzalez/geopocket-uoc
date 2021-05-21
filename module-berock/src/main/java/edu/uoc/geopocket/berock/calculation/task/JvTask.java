package edu.uoc.geopocket.berock.calculation.task;

import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.entities.Joint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class JvTask extends AbstractBerockTaskExecutable {

    public JvTask() {
        super(BerockTask.CALCULATE_JV);
    }

    public void execute(final Berock berock) {
        BigDecimal result = BigDecimal.valueOf(0);
        for (Joint joint : berock.getJoints()) {
            result = result.add(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(joint.getSpacing()), MATH_CONTEXT));
        }
        berock.getResult().setVolumetricJointCount(result);
        log.info(String.format("Jv: %s", berock.getResult().getVolumetricJointCount()));
    }
}
