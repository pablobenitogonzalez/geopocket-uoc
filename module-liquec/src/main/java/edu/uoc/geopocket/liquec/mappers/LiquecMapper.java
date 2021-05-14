package edu.uoc.geopocket.liquec.mappers;

import edu.uoc.geopocket.common.mappers.AbstractToolMapper;
import edu.uoc.geopocket.liquec.dtos.LiquecDTO;
import edu.uoc.geopocket.liquec.entities.Liquec;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class LiquecMapper extends AbstractToolMapper<Liquec, LiquecDTO> {
}
