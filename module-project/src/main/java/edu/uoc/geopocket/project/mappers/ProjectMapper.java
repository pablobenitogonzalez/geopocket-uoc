package edu.uoc.geopocket.project.mappers;

import edu.uoc.geopocket.common.mappers.AbstractMapper;
import edu.uoc.geopocket.project.dtos.ProjectDTO;
import edu.uoc.geopocket.project.entities.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProjectMapper extends AbstractMapper<Project, ProjectDTO> {
}
