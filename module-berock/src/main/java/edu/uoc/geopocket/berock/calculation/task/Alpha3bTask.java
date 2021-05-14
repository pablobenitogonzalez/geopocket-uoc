package edu.uoc.geopocket.berock.calculation.task;

import ch.obermuhlner.math.big.BigDecimalMath;
import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.entities.Berock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.FloatingDecimal;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class Alpha3bTask extends AbstractBerockTaskExecutable {

    public Alpha3bTask() {
        super(BerockTask.CALCULATE_ALPHA_3B);
    }

    public void execute(final Berock berock) {
        BigDecimal RDQ = berock.getResult().getCalculatedRDQ();
        if (Objects.nonNull(berock.getRockQualityDesignation())) {
            RDQ = BigDecimal.valueOf(berock.getRockQualityDesignation());
        }
        log.info(String.format("Selected RDQ (percentage): %s", RDQ));
        berock.getResult().setAlpha3b(BigDecimalMath.sqrt(RDQ.divide(BigDecimal.valueOf(100.0), MATH_CONTEXT), MATH_CONTEXT));
        log.info(String.format("Alpha 3b: %s", berock.getResult().getAlpha3b()));
    }
}
