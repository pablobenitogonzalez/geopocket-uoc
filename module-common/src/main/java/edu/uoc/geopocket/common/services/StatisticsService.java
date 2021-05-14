package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Summary;
import edu.uoc.geopocket.common.Usage;

import java.util.List;

public interface StatisticsService {
    Long countAll();
    List<Usage> countUsages(final Long projectId, Status status);
    List<Summary> getSummaries(final Long projectId);
    Long count(final Status status);
    List<Summary> getCurrentSummaries(final int amount);
}
