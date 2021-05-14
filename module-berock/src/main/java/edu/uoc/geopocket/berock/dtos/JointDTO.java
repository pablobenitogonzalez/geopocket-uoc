package edu.uoc.geopocket.berock.dtos;

import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JointDTO extends JointInputDTO {
    private AuditDTO audit;
}
