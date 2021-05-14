package edu.uoc.geopocket.statistics.common;

import edu.uoc.geopocket.common.Tool;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolMetric {
    private Tool tool;
    private Long total;
    private Float percentage;
}
