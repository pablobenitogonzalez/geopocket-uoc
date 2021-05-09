package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import edu.uoc.geopocket.project.dtos.ProjectDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LiquecDTO extends LiquecBaseDTO {
    private Long id;
    private ProjectDTO project;
    private Status status;
    private List<SoilLayerDTO> soilLayers;
    private List<SptDTO> spts;
    private AuditDTO audit;
}
