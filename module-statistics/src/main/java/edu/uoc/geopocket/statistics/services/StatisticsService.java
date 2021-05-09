package edu.uoc.geopocket.statistics.services;

import edu.uoc.geopocket.statistics.common.ToolCount;
import edu.uoc.geopocket.statistics.common.ToolUsage;

import java.util.List;

public interface StatisticsService {
    List<ToolCount> countAll();
    List<ToolUsage> getUsages(Long projectId);
}
