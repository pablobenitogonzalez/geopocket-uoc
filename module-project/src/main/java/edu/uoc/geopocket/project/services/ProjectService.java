package edu.uoc.geopocket.project.services;

import edu.uoc.geopocket.project.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Page<Project> findAll(final Pageable pageable);

    Page<Project> findAllByUser(final Pageable pageable);

    Project get(final Long id);

    Project save(final Project project);

    void delete(final Long id);

}
