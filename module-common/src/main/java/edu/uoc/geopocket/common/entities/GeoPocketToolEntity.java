package edu.uoc.geopocket.common.entities;

import edu.uoc.geopocket.common.Status;

public interface GeoPocketToolEntity extends GeoPocketEntity {
    GeoPocketProjectEntity getProject();
    Status getStatus();
    Audit getAudit();
    CalculationInfo getCalculationInfo();
}
