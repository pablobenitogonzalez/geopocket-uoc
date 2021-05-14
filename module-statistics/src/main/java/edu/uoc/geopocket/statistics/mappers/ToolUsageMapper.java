package edu.uoc.geopocket.statistics.mappers;

import edu.uoc.geopocket.common.mappers.AbstractMapper;
import edu.uoc.geopocket.statistics.common.ToolUsage;
import edu.uoc.geopocket.statistics.dtos.ToolUsageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ToolUsageMapper extends AbstractMapper<ToolUsage, ToolUsageDTO> {
}
