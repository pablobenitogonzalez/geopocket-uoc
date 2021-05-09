package edu.uoc.geopocket.statistics.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Usage;
import edu.uoc.geopocket.statistics.common.Tool;
import edu.uoc.geopocket.statistics.common.ToolCount;
import edu.uoc.geopocket.statistics.common.ToolUsage;
import edu.uoc.geopocket.statistics.factory.ToolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private ToolFactory toolFactory;

    @Autowired
    public StatisticsServiceImpl(ToolFactory toolFactory) {
        this.toolFactory = toolFactory;
    }

    @Override
    public List<ToolCount> countAll() {
        final List<ToolCount> toolCounts = new ArrayList<>();
        Arrays.stream(Tool.values()).forEach(tool ->
                toolCounts.add(ToolCount.builder().tool(tool).count(toolFactory.getToolService(tool).countAll()).build()));
        return toolCounts;
    }

    @Override
    public List<ToolUsage> getUsages(Long projectId) {
        final List<ToolUsage> toolUsages = new ArrayList<>();
        Arrays.stream(Tool.values()).forEach(tool -> Arrays.stream(Status.values()).forEach(status -> {
            final List<Usage> usages = toolFactory.getToolService(tool).countUsages(projectId, status);
            toolUsages.add(ToolUsage.builder().tool(tool).status(status).usages(usages).build());
        }));
        return toolUsages;
    }
}
