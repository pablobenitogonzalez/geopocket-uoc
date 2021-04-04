package edu.uoc.geopocket.project.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectBaseDTO {
    @NotEmpty
    private String name;
    private String location;
    private String organization;
}
