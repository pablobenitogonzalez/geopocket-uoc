package edu.uoc.geopocket.liquec.repositories;

import edu.uoc.geopocket.common.repositories.ToolRepository;
import edu.uoc.geopocket.liquec.entities.Liquec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface LiquecRepository extends ToolRepository<Liquec, Long> {
    @Query("select distinct l.audit.createdBy from Liquec l where l.project.id = :projectId")
    Set<String> findDistinctUsers(@Param("projectId") Long projectId);
}
