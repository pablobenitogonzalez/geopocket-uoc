package edu.uoc.geopocket.statistics.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Summary;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.Usage;
import edu.uoc.geopocket.common.entities.Audit;
import edu.uoc.geopocket.project.services.ProjectService;
import edu.uoc.geopocket.statistics.common.*;
import edu.uoc.geopocket.statistics.factory.ToolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final int CURRENT_SUMMARY_AMOUNT = 2;

    private final ToolFactory toolFactory;

    private final ProjectService projectService;

    @Autowired
    public StatisticsServiceImpl(final ToolFactory toolFactory, final ProjectService projectService) {
        this.toolFactory = toolFactory;
        this.projectService = projectService;
    }

    @Override
    public Statistics getStatistics() {
        final Statistics statistics = new Statistics();
        statistics.setProjects(projectService.countProjects());
        final List<ToolStatusMetric> toolStatusMetrics = new ArrayList<>();
        Arrays.stream(Tool.values()).forEach(tool -> Arrays.stream(Status.values()).forEach(status ->
                toolStatusMetrics.add(ToolStatusMetric.builder().tool(tool).status(status)
                        .total(toolFactory.getToolService(tool).count(status)).build())));
        statistics.setToolStatusMetrics(toolStatusMetrics);
        final Long total = Optional.ofNullable(statistics.getToolStatusMetrics()).orElse(Collections.emptyList()).stream()
                .map(ToolStatusMetric::getTotal).collect(Collectors.toList()).stream().reduce(0L, Long::sum);
        Optional.ofNullable(statistics.getToolStatusMetrics()).orElse(Collections.emptyList())
                .forEach(toolStatusMetric -> toolStatusMetric.setPercentage(getPercentage(toolStatusMetric.getTotal(), total)));
        final List<ToolMetric> toolMetrics = new ArrayList<>();
        Arrays.stream(Tool.values()).forEach(tool -> {
            final Long totalTool = Optional.ofNullable(statistics.getToolStatusMetrics()).orElse(Collections.emptyList())
                    .stream().filter(toolStatusMetric -> tool.equals(toolStatusMetric.getTool()))
                    .map(ToolStatusMetric::getTotal).collect(Collectors.toList()).stream().reduce(0L, Long::sum);
            toolMetrics.add(ToolMetric.builder().tool(tool).total(totalTool).percentage((getPercentage(totalTool, total))).build());
        });
        statistics.setToolMetrics(toolMetrics);
        final List<Summary> summaries = Arrays.stream(Tool.values())
                .map(tool -> toolFactory.getToolService(tool).getCurrentSummaries(CURRENT_SUMMARY_AMOUNT))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Summary::getAudit, Comparator.comparing(Audit::getUpdatedOn)).reversed())
                .collect(Collectors.toList());
        statistics.setSummaries(summaries);
        return statistics;
    }

    private float getPercentage(final long partial, long total) {
        if (total == 0L) return 0.0f;
        return ((float) (partial*100)/total);
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
        Set<String> users = Optional.of(toolUsages).orElse(Collections.emptyList()).stream().map(ToolUsage::getUsages)
                .flatMap(Collection::stream).map(Usage::getUser).collect(Collectors.toSet());
        Optional.of(toolUsages).orElse(Collections.emptyList()).forEach(toolUsage -> {
            Optional.of(users).orElse(Collections.emptySet()).forEach(user -> {
                final Usage u = Optional.ofNullable(toolUsage.getUsages()).orElse(Collections.emptyList()).stream()
                        .filter(usage -> usage.getUser().equals(user)).findFirst().orElse(null);
                if (Objects.isNull(u)) {
                    Objects.requireNonNull(toolUsage.getUsages()).add(Usage.builder().user(user).usage(0L).build());
                }
            });
            toolUsage.getUsages().sort(Comparator.comparing(Usage::getUser));
        });
        return toolUsages;
    }

    @Override
    public List<Summary> getSummaries(Long projectId) {
        return Arrays.stream(Tool.values()).map(tool -> toolFactory.getToolService(tool).getSummaries(projectId))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }
}
