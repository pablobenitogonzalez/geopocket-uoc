package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.liquec.common.LiquecStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiquecSearchDTO {
    private Long projectId;
    private LiquecStatus status;
}
