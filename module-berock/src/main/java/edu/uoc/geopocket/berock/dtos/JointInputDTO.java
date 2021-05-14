package edu.uoc.geopocket.berock.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JointInputDTO {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.01", message = "Spacing must be at least 0.01")
    @DecimalMax(value = "30.00", message = "Spacing must be a maximum of 30.0")
    private Float spacing;
    private String family;
}
