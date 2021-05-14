package edu.uoc.geopocket.liquec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class SptInputDTO {
    private Long id;
    @NotNull
    @DecimalMin(value = "0.01", message = "Depth must be at least 0.01")
    @DecimalMax(value = "30.00", message = "Depth must be a maximum of 30.0")
    private Float depth;
    @NotNull
    @Min(value = 0, message = "SPT blow counts should not be less than 0")
    @Max(value = 50, message = "SPT blow counts should not be greater than 50")
    private Integer sptBlowCounts;
    @NotNull
    @DecimalMin(value = "0.00", message = "Energy ratio must be at least 0.0")
    @DecimalMax(value = "100.00", message = "Energy ratio must be a maximum of 100.0")
    private Float energyRatio;
}
