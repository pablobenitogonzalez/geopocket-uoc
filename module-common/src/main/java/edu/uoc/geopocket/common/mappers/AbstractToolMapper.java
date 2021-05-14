package edu.uoc.geopocket.common.mappers;

import edu.uoc.geopocket.common.dtos.GeoPocketToolDTO;
import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractToolMapper<E, DTO extends GeoPocketToolDTO> extends AbstractMapper<E, DTO>  {

    @Autowired
    private SecurityContextHelper securityContextHelper;

    @AfterMapping
    protected void setCalculationInfo(@MappingTarget DTO dto) {
        if (!securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            dto.setCalculationInfo(null);
        }
    }

}
