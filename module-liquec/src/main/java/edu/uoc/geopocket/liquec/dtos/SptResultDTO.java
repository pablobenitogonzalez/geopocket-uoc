package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SptResultDTO {
    private Long id;
    private Result result;
    private String message;
    private Double totalStress;
    private Double interstitialStress;
    private Double effectiveStress;
    private Double effectiveStressFactor;
    private Double earthquakeMagnitudeCorrection;
    private Double coefficientContributionCorrection;
    private Double sptCorrected;
    private Double cycleStressRatio;
    private Double depthFactor;
    private Double cycleResistanceRatio;
    private Double cycleResistanceRatioCorrected;
    private Double safetyFactor;
    private AuditDTO audit;
}
