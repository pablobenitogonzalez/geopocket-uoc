package edu.uoc.geopocket.common.dtos;

import edu.uoc.geopocket.common.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {
    private Long projectId;
    private Status status;
}
