package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.common.Summary;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDTO {
    private Long projects;
    private List<ToolStatusMetricDTO> toolStatusMetrics;
    private List<ToolMetricDTO> toolMetrics;
    private List<Summary> summaries;
}
