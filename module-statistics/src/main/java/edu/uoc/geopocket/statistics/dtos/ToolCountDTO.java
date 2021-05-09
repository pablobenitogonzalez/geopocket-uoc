package edu.uoc.geopocket.statistics.dtos;

import edu.uoc.geopocket.statistics.common.Tool;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolCountDTO {
    private Tool tool;
    private Long count;
}
