package edu.uoc.geopocket.statistics.common;

import edu.uoc.geopocket.common.Tool;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolCount {
    private Tool tool;
    private Long count;
}
