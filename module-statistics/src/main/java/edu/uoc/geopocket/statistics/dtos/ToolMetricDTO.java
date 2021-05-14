package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.common.Tool;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolMetricDTO {
    private Tool tool;
    private Long total;
    private Float percentage;
}
