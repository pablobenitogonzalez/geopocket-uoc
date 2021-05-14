package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.dtos.GeoPocketInputToolDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class LiquecInputDTO extends LiquecBaseDTO implements GeoPocketInputToolDTO {
    private Long id;
    @NotNull
    private Long projectId;
    @Valid
    private List<SoilLayerInputDTO> soilLayers;
    @Valid
    private List<SptInputDTO> spts;
}
