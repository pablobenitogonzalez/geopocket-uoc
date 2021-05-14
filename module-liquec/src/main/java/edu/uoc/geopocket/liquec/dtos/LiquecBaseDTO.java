package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.validation.Conditional;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Conditional(
        selected = "code",
        value = "EUROCODE",
        required = "earthquakeMagnitude",
        decimalMin = "0.01",
        decimalMax = "10.0",
        message = "Earthquake magnitude must be between 0.01 and 10.0"
)
@Conditional(
        selected = "code",
        value = "NCSE_02",
        required = "coefficientOfContribution",
        decimalMin = "1.0",
        decimalMax = "2.0",
        message = "Coefficient of contribution must be between 1.0 and 2.0"
)
public class LiquecBaseDTO {
    @NotNull
    private LiquecCode code;
    @NotNull
    @DecimalMin(value = "0.01", message = "Peak ground acceleration must be at least 0.01")
    @DecimalMax(value = "10.00", message = "Peak ground acceleration must be a maximum of 10.0")
    private Float peakGroundAcceleration;
    private Float earthquakeMagnitude;
    private Float coefficientOfContribution;
    @NotNull
    @DecimalMin(value = "0.00", message = "Ground water table depth must be at least 0.00")
    @DecimalMax(value = "30.00", message = "Ground water table depth must be a maximum of 30.0")
    private Float groundWaterTableDepth;
}
