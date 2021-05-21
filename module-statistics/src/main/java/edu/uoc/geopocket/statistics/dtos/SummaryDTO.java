package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummaryDTO {
    private Tool tool;
    private Long id;
    private Long projectId;
    private Status status;
    private AuditDTO audit;
}
