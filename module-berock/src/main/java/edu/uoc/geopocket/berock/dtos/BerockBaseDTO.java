package edu.uoc.geopocket.berock.dtos;

import edu.uoc.geopocket.berock.common.RockType;
import edu.uoc.geopocket.berock.common.WeatheringDegree;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BerockBaseDTO {
    @NotNull
    private RockType rockType;
    @NotNull
    private WeatheringDegree weatheringDegree;
    @DecimalMin(value = "0.00", message = "If present, RQD must be at least 0.00")
    @DecimalMax(value = "100.00", message = "If present, RQD must be a maximum of 100.0")
    private Float rockQualityDesignation;
    @NotNull
    @DecimalMin(value = "0.01", message = "uniaxial compressive strength must be at least 0.01")
    @DecimalMax(value = "1000.00", message = "uniaxial compressive strength must be a maximum of 1000.0")
    private Float uniaxialCompressiveStrength;
}
