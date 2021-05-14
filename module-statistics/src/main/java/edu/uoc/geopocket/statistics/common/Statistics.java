package edu.uoc.geopocket.statistics.common;

import edu.uoc.geopocket.common.Summary;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistics {
    private Long projects;
    private List<ToolStatusMetric> toolStatusMetrics;
    private List<ToolMetric> toolMetrics;
    private List<Summary> summaries;
}
