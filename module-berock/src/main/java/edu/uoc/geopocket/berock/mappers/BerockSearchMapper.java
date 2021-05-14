package edu.uoc.geopocket.berock.mappers;

import edu.uoc.geopocket.berock.common.BerockSearch;
import edu.uoc.geopocket.berock.dtos.BerockSearchDTO;
import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BerockSearchMapper extends AbstractInputMapper<BerockSearch, BerockSearchDTO> {
}
