package edu.uoc.geopocket.statistics.services;

import edu.uoc.geopocket.common.Summary;
import edu.uoc.geopocket.statistics.common.Statistics;
import edu.uoc.geopocket.statistics.common.ToolCount;
import edu.uoc.geopocket.statistics.common.ToolUsage;

import java.util.List;

public interface StatisticsService {
    Statistics getStatistics();
    List<ToolCount> countAll();
    List<ToolUsage> getUsages(Long projectId);
    List<Summary> getSummaries(Long projectId);
}
