package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Usage;

import java.util.List;

public interface ToolService {
    Long countAll();
    List<Usage> countUsages(final Long projectId, Status status);
}
