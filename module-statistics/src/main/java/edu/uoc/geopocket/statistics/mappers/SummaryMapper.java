package edu.uoc.geopocket.statistics.mappers;

import edu.uoc.geopocket.common.Summary;
import edu.uoc.geopocket.common.mappers.AbstractMapper;
import edu.uoc.geopocket.statistics.dtos.SummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SummaryMapper extends AbstractMapper<Summary, SummaryDTO> {
}
