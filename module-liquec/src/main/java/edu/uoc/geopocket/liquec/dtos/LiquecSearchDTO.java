package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiquecSearchDTO {
    private Long projectId;
    private Status status;
}
