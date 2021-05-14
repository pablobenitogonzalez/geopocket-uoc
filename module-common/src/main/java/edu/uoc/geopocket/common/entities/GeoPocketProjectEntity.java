package edu.uoc.geopocket.common.entities;

public interface GeoPocketProjectEntity extends GeoPocketEntity {
    String getUser();
    String getName();
    String getLocation();
    String getOrganization();
    Audit getAudit();
}
