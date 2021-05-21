package edu.uoc.geopocket.statistics.controllers;

import edu.uoc.geopocket.statistics.dtos.StatisticsDTO;
import edu.uoc.geopocket.statistics.dtos.SummaryDTO;
import edu.uoc.geopocket.statistics.dtos.ToolCountDTO;
import edu.uoc.geopocket.statistics.dtos.ToolUsageDTO;
import edu.uoc.geopocket.statistics.mappers.StatisticsMapper;
import edu.uoc.geopocket.statistics.mappers.SummaryMapper;
import edu.uoc.geopocket.statistics.mappers.ToolCountMapper;
import edu.uoc.geopocket.statistics.mappers.ToolUsageMapper;
import edu.uoc.geopocket.statistics.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final StatisticsMapper statisticsMapper;

    private final ToolCountMapper toolCountMapper;

    private final ToolUsageMapper toolUsageMapper;

    private final SummaryMapper summaryMapper;

    @Autowired
    public StatisticsController(final StatisticsService statisticsService, final StatisticsMapper statisticsMapper,
                                final ToolCountMapper toolCountMapper, final ToolUsageMapper toolUsageMapper,
                                final SummaryMapper summaryMapper) {
        this.statisticsService = statisticsService;
        this.statisticsMapper = statisticsMapper;
        this.toolCountMapper = toolCountMapper;
        this.toolUsageMapper = toolUsageMapper;
        this.summaryMapper = summaryMapper;
    }

    @GetMapping("/dashboard")
    @ResponseStatus(HttpStatus.OK)
    public StatisticsDTO getStatistics() {
        return statisticsMapper.toDTO(statisticsService.getStatistics());
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolCountDTO> countAll() {
        return toolCountMapper.toDTOs(statisticsService.countAll());
    }

    @GetMapping("/{id}/usages")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolUsageDTO> getUsages(final @PathVariable("id") Long projectId) {
        return toolUsageMapper.toDTOs(statisticsService.getUsages(projectId));
    }

    @GetMapping("/{id}/summaries")
    @ResponseStatus(HttpStatus.OK)
    public List<SummaryDTO> getSummaries(final @PathVariable("id") Long projectId) {
        return summaryMapper.toDTOs(statisticsService.getSummaries(projectId));
    }
}
