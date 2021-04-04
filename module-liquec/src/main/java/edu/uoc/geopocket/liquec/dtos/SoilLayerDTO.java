package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoilLayerDTO extends SoilLayerInputDTO {
    private AuditDTO audit;
}
