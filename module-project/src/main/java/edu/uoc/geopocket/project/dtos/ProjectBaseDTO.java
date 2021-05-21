package edu.uoc.geopocket.project.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class ProjectBaseDTO {
    @Size(min = 3, max = 60, message = "Name must be between 3 and 60 characters")
    private String name;
    @Size(min = 3, max = 60, message = "Location must be between 3 and 60 characters")
    private String location;
    @Size(min = 3, max = 60, message = "Organization must be between 3 and 60 characters")
    private String organization;
}
