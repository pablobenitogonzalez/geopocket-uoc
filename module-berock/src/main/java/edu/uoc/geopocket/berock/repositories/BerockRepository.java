package edu.uoc.geopocket.berock.repositories;

import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.common.repositories.ToolRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BerockRepository extends ToolRepository<Berock, Long> {
    @Query("select distinct b.audit.createdBy from Berock b where b.project.id = :projectId")
    Set<String> findDistinctUsers(@Param("projectId") Long projectId);
}
