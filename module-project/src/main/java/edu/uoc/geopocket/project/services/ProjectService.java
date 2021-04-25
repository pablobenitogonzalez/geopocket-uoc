package edu.uoc.geopocket.project.services;

import edu.uoc.geopocket.project.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    Page<Project> findAll(final Pageable pageable);

    List<Project> autocomplete(final String projectName);

    Project get(final Long id);

    Project save(final Project project);

    void delete(final Long id);

}
