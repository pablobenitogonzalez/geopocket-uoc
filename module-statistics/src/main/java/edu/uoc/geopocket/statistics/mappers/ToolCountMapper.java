package edu.uoc.geopocket.statistics.mappers;

import edu.uoc.geopocket.common.mappers.AbstractMapper;
import edu.uoc.geopocket.statistics.common.Statistics;
import edu.uoc.geopocket.statistics.common.ToolCount;
import edu.uoc.geopocket.statistics.dtos.StatisticsDTO;
import edu.uoc.geopocket.statistics.dtos.ToolCountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ToolCountMapper extends AbstractMapper<ToolCount, ToolCountDTO> {
}
