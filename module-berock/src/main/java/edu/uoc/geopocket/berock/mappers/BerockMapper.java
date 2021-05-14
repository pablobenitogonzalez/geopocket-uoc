package edu.uoc.geopocket.berock.mappers;

import edu.uoc.geopocket.berock.dtos.BerockDTO;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.common.mappers.AbstractToolMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BerockMapper extends AbstractToolMapper<Berock, BerockDTO> {
}
