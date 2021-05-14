package edu.uoc.geopocket.berock.dtos;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import edu.uoc.geopocket.common.dtos.CalculationInfoDTO;
import edu.uoc.geopocket.common.dtos.GeoPocketToolDTO;
import edu.uoc.geopocket.project.dtos.ProjectDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BerockDTO extends BerockBaseDTO implements GeoPocketToolDTO {
    private Long id;
    private ProjectDTO project;
    private Status status;
    private List<JointDTO> joints;
    private BerockResultDTO result;
    private AuditDTO audit;
    private CalculationInfoDTO calculationInfo;
}
