package edu.uoc.geopocket.berock.dtos;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.dtos.AuditDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BerockResultDTO {
    private Long id;
    private Result result;
    private String message;
    private BigDecimal allowableBearingPressure;
    private BigDecimal alpha3a;
    private BigDecimal alpha3b;
    private BigDecimal finalAlpha3;
    private BigDecimal volumetricJointCount;
    private BigDecimal calculatedRDQ;
    private AuditDTO audit;
}

