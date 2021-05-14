package edu.uoc.geopocket.liquec.mappers;

import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import edu.uoc.geopocket.liquec.dtos.LiquecInputDTO;
import edu.uoc.geopocket.liquec.entities.Liquec;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LiquecInputMapper extends AbstractInputMapper<Liquec, LiquecInputDTO> {
}
