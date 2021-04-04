package edu.uoc.geopocket.liquec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class LiquecInputDTO extends LiquecBaseDTO {
    private Long id;
    @NotNull
    private Long projectId;
    private List<SoilLayerInputDTO> soilLayers;
    private List<SptInputDTO> spts;
}
