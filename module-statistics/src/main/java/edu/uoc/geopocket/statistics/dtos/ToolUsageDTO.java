package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolUsageDTO {
    private Tool tool;
    private Status status;
    private List<UsageDTO> usages;
}
