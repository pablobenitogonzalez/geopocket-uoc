package edu.uoc.geopocket.project.services;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.repositories.ProjectRepository;
import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_ADMIN)) {
            return repository.findAll(pageable);
        }
        return repository.findAllByUser(securityContextHelper.getUserName(), pageable);
    }

    @Override
    public List<Project> autocomplete(String projectName) {
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_ADMIN)) {
            return repository.findAllByNameContainingIgnoreCaseOrderByNameAsc(projectName);
        }
        return repository.findAllByUserAndNameContainingIgnoreCaseOrderByNameAsc(securityContextHelper.getUserName(), projectName);
    }

    @Override
    public Project get(final Long id) {
        final Project project = repository.findById(id).orElseThrow(() -> new GeoPocketException(String.format("Project %s not found", id)));
        if (!securityContextHelper.hasRole(GeoPocketRole.ROLE_ADMIN) && !project.getUser().equals(securityContextHelper.getUserName())) {
            throw new GeoPocketException("Different project owner");
        }
        return project;
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
