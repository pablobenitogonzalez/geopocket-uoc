package edu.uoc.geopocket.berock.calculation.task;

import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.entities.Berock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class FinalAlpha3Task extends AbstractBerockTaskExecutable {

    public FinalAlpha3Task() {
        super(BerockTask.CALCULATE_FINAL_ALPHA_3);
    }

    public void execute(final Berock berock) {
        BigDecimal alpha3 = berock.getResult().getAlpha3a();
        if (berock.getResult().getAlpha3b().compareTo(alpha3) < 0) {
            alpha3 = berock.getResult().getAlpha3b();
        }
        berock.getResult().setFinalAlpha3(alpha3);
        log.info(String.format("Final alpha 3: %s", alpha3));
    }
}
