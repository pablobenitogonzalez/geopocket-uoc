package edu.uoc.geopocket.statistics.common;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.Usage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolUsage {
    private Tool tool;
    private Status status;
    private List<Usage> usages;
}
