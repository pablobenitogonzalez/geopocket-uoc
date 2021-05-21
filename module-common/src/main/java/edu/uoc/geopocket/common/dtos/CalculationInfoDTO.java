package edu.uoc.geopocket.common.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CalculationInfoDTO {
    private String buildVersion;
    private String buildGroup;
    private String buildArtifact;
    private Instant buildTime;
    private String activeProfiles;
    private Long elapsedTime;
}
