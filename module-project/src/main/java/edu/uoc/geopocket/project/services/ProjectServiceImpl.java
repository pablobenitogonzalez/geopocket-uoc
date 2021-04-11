package edu.uoc.geopocket.project.services;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.repositories.ProjectRepository;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository repository;

    private SecurityContextHelper securityContextHelper;

    @Autowired
    public ProjectServiceImpl(final ProjectRepository repository, final SecurityContextHelper securityContextHelper) {
        this.repository = repository;
        this.securityContextHelper = securityContextHelper;
    }

    @Override
    public Page<Project> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Project> findAllByUser(final Pageable pageable) {
        return repository.findAllByUser(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
    }

    @Override
    public Project get(final Long id) {
        return repository.findById(id).orElseThrow(() -> new GeoPocketException(String.format("Project %s not found", id)));
    }

    @Override
    public Project save(final Project project) {
        if (Objects.isNull(project.getId())) {
            project.setUser(securityContextHelper.getUserName());
            return repository.save(project);
        }
        final Project projectDBO = get(project.getId());
        projectDBO.setName(project.getName());
        projectDBO.setLocation(project.getLocation());
        projectDBO.setOrganization(project.getOrganization());
        return repository.save(projectDBO);
    }

    @Override
    public void delete(final Long id) {
        final Project project = get(id);
        repository.delete(project);
    }

}
