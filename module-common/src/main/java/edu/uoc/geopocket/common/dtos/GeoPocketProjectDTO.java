package edu.uoc.geopocket.common.dtos;

public interface GeoPocketProjectDTO extends GeoPocketDTO {
    String getUser();
    String getName();
    String getLocation();
    String getOrganization();
    AuditDTO getAudit();
}
