package edu.uoc.geopocket.common.repositories;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

@NoRepositoryBean
public interface ToolRepository<T extends GeoPocketToolEntity, ID> extends JpaRepository<T, ID> {
    Long countByProjectIdAndStatusAndAuditCreatedBy(Long projectId, Status status, String user);
    Long countByAuditCreatedBy(String user);
    Long countByStatus(Status status);
    Long countByStatusAndAuditCreatedBy(Status status, String user);
    Set<String> findDistinctUsers(Long projectId);
    List<T> findAllByProjectId(Long projectId);
    List<T> findAllByProjectIdAndAuditCreatedBy(Long projectId, String user);
}
