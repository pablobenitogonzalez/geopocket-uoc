package edu.uoc.geopocket.project.mappers;

import edu.uoc.geopocket.common.mappers.AbstractInputMapper;
import edu.uoc.geopocket.project.dtos.ProjectInputDTO;
import edu.uoc.geopocket.project.entities.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProjectInputMapper extends AbstractInputMapper<Project, ProjectInputDTO> {
}
