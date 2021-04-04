package edu.uoc.geopocket.liquec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SoilLayerInputDTO {
    private Long id;
    @NotNull
    private Float startDepth;
    @NotNull
    private Float finalDepth;
    @NotNull
    private String soilType;
    @NotNull
    private Float aboveGwt;
    @NotNull
    private Float belowGwt;
    @NotNull
    private Float finesContent;
    @NotNull
    private Boolean checkLiquefaction;
}
