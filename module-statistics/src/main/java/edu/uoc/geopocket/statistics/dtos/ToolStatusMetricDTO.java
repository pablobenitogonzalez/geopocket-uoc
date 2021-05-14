package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolStatusMetricDTO {
    private Tool tool;
    private Status status;
    private Long total;
    private Float percentage;
}
