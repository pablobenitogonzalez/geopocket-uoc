package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.liquec.common.LiquecCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LiquecBaseDTO {
    @NotNull
    private LiquecCode code;
    @NotNull
    private Float peakGroundAcceleration;
    @NotNull
    private Float earthquakeMagnitude;
    @NotNull
    private Float coefficientOfContribution;
    @NotNull
    private Float groundWaterTableDepth;
}
