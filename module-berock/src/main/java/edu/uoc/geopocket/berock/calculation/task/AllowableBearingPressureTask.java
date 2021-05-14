package edu.uoc.geopocket.berock.calculation.task;

import ch.obermuhlner.math.big.BigDecimalMath;
import edu.uoc.geopocket.berock.calculation.AbstractBerockTaskExecutable;
import edu.uoc.geopocket.berock.calculation.BerockTask;
import edu.uoc.geopocket.berock.entities.Berock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class AllowableBearingPressureTask extends AbstractBerockTaskExecutable {

    private static final BigDecimal REFERENCE_PRESSURE = BigDecimal.valueOf(1.0);

    public AllowableBearingPressureTask() {
        super(BerockTask.CALCULATE_ALLOWABLE_BEARING_PRESSURE);
    }

    public void execute(final Berock berock) {
        final BigDecimal allowableBearingPressure = REFERENCE_PRESSURE.multiply(berock.getRockType().getAlpha())
                .multiply(berock.getWeatheringDegree().getAlpha())
                .multiply(berock.getResult().getFinalAlpha3())
                .multiply(BigDecimalMath.sqrt((BigDecimal.valueOf(berock.getUniaxialCompressiveStrength()))
                        .divide(REFERENCE_PRESSURE, MATH_CONTEXT), MATH_CONTEXT));
        berock.getResult().setAllowableBearingPressure(allowableBearingPressure);
        log.info(String.format("Allowable Bearing Pressure (MPa): %s", allowableBearingPressure));
    }
}
