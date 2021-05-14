package edu.uoc.geopocket.liquec.mappers;

import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.dtos.LiquecSearchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LiquecSearchMapper extends AbstractInputMapper<LiquecSearch, LiquecSearchDTO> {
}
