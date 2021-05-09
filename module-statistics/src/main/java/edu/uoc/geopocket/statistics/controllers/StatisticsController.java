package edu.uoc.geopocket.statistics.controllers;

import edu.uoc.geopocket.statistics.dtos.ToolCountDTO;
import edu.uoc.geopocket.statistics.dtos.ToolUsageDTO;
import edu.uoc.geopocket.statistics.services.StatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;

    private ModelMapper modelMapper;

    @Autowired
    public StatisticsController(final StatisticsService statisticsService, final ModelMapper modelMapper) {
        this.statisticsService = statisticsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolCountDTO> countAll() {
        return Optional.ofNullable(statisticsService.countAll()).orElse(Collections.emptyList())
                .stream().map(toolCount -> modelMapper.map(toolCount, ToolCountDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ToolUsageDTO> getUsages(final @PathVariable("id") Long projectId) {
        return Optional.ofNullable(statisticsService.getUsages(projectId)).orElse(Collections.emptyList())
                .stream().map(toolUsage -> modelMapper.map(toolUsage, ToolUsageDTO.class))
                .collect(Collectors.toList());
    }
}
