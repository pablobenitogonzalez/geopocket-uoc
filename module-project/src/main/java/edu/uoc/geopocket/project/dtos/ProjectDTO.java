package edu.uoc.geopocket.project.dtos;

import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO extends ProjectBaseDTO {
    private Long id;
    private String user;
    private AuditDTO audit;
}
