package edu.uoc.geopocket.common.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Getter
@Setter
@Embeddable
public class CalculationInfo {

    @Column(name = "BUILD_VERSION")
    private String buildVersion;

    @Column(name = "BUILD_GROUP")
    private String buildGroup;

    @Column(name = "BUILD_ARTIFACT")
    private String buildArtifact;

    @Column(name = "BUILD_TIME")
    private Instant buildTime;

    @Column(name = "ACTIVE_PROFILES")
    private String activeProfiles;

    @Column(name = "ELAPSED_TIME")
    private Long elapsedTime;

}
