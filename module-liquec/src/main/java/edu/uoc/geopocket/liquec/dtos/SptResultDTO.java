package edu.uoc.geopocket.liquec.dtos;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SptResultDTO {
    private Long id;
    private Result result;
    private String message;
    private BigDecimal totalStress;
    private BigDecimal interstitialStress;
    private BigDecimal effectiveStress;
    private BigDecimal effectiveStressFactor;
    private BigDecimal earthquakeMagnitudeCorrection;
    private BigDecimal coefficientContributionCorrection;
    private BigDecimal sptCorrected;
    private BigDecimal cycleStressRatio;
    private BigDecimal depthFactor;
    private BigDecimal cycleResistanceRatio;
    private BigDecimal cycleResistanceRatioCorrected;
    private BigDecimal safetyFactor;
    private AuditDTO audit;
}
