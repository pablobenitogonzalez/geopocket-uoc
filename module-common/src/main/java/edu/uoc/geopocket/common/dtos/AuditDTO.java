package edu.uoc.geopocket.common.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditDTO {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private String createdBy;
    private String updatedBy;
}
