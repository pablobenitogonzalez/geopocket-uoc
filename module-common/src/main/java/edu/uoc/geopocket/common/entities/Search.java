package edu.uoc.geopocket.common.entities;

import edu.uoc.geopocket.common.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
    private Long projectId;
    private Status status;
}
