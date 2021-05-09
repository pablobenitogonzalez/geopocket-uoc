package edu.uoc.geopocket.common.repositories;

import edu.uoc.geopocket.common.Status;

public interface ToolRepository<T> {
    Long countByProjectIdAndStatusAndAuditCreatedBy(Long projectId, Status status, String user);
    Long countByAuditCreatedBy(String user);
}
