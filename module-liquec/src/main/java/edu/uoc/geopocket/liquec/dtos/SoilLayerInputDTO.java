package edu.uoc.geopocket.liquec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SoilLayerInputDTO {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.0", message = "Start depth must be at least 0.0")
    @DecimalMax(value = "30.00", message = "Start depth must be a maximum of 30.0")
    private Float startDepth;
    @NotNull
    @DecimalMin(value = "0.0", message = "Final depth must be at least 0.0")
    @DecimalMax(value = "30.00", message = "Final depth must be a maximum of 30.0")
    private Float finalDepth;
    @NotNull
    @Size(min = 3, max = 60, message = "Soil type must be between 3 and 60 characters")
    private String soilType;
    @NotNull
    @DecimalMin(value = "11.00", message = "Above gwt must be at least 11.0")
    @DecimalMax(value = "50.00", message = "Above gwt must be a maximum of 50.0")
    private Float aboveGwt;
    @NotNull
    @DecimalMin(value = "11.00", message = "Below gwt must be at least 11.0")
    @DecimalMax(value = "50.00", message = "Below gwt must be a maximum of 50.0")
    private Float belowGwt;
    @NotNull
    @DecimalMin(value = "0.00", message = "Fines content must be at least 0.0")
    @DecimalMax(value = "100.00", message = "Fines content must be a maximum of 100.0")
    private Float finesContent;
    @NotNull
    private Boolean checkLiquefaction;
}
