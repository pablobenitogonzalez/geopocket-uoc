package edu.uoc.geopocket.common;

import edu.uoc.geopocket.common.entities.Audit;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Summary {
    private Tool tool;
    private Long id;
    private Long projectId;
    private Status status;
    private Audit audit;
}
