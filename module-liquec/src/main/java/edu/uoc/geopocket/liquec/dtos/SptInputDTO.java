package edu.uoc.geopocket.liquec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SptInputDTO {
    private Long id;
    @NotNull
    private Float depth;
    @NotNull
    private Integer sptBlowCounts;
    @NotNull
    private Float energyRatio;
}
