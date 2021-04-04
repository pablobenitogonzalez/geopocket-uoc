package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SptDTO extends SptInputDTO {
    private SptResultDTO sptResult;
    private AuditDTO audit;
}
