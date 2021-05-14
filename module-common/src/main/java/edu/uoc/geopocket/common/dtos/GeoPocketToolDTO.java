package edu.uoc.geopocket.common.dtos;

import edu.uoc.geopocket.common.Status;

public interface GeoPocketToolDTO extends GeoPocketDTO {
    GeoPocketProjectDTO getProject();
    Status getStatus();
    AuditDTO getAudit();
    CalculationInfoDTO getCalculationInfo();
    void setCalculationInfo(CalculationInfoDTO calculationInfo);
}
