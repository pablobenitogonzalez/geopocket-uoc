package edu.uoc.geopocket.berock.mappers;

import edu.uoc.geopocket.berock.dtos.BerockInputDTO;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BerockInputMapper extends AbstractInputMapper<Berock, BerockInputDTO> {
}
