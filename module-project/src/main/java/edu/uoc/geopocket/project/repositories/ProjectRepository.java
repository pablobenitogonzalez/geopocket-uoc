package edu.uoc.geopocket.project.repositories;

import edu.uoc.geopocket.project.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByUser(String user, Pageable var1);
}
