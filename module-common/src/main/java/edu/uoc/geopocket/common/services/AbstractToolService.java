package edu.uoc.geopocket.common.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Summary;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.Usage;
import edu.uoc.geopocket.common.criterias.AbstractToolCriteria;
import edu.uoc.geopocket.common.entities.CalculationInfo;
import edu.uoc.geopocket.common.entities.GeoPocketProjectEntity;
import edu.uoc.geopocket.common.entities.GeoPocketToolEntity;
import edu.uoc.geopocket.common.entities.Search;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.repositories.ToolRepository;
import edu.uoc.geopocket.security.common.GeoPocketRole;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractToolService<T extends GeoPocketToolEntity, S extends Search> {

    protected Tool tool;

    protected Class<S> searchClass;

    protected BuildProperties buildProperties;

    protected Environment environment;

    protected ToolRepository<T, Long> repository;

    protected AbstractToolCriteria<T, Long, S> criteria;

    protected SecurityContextHelper securityContextHelper;

    public AbstractToolService(final Tool tool, final Class<S> searchClass, final BuildProperties buildProperties,
                               final Environment environment, final ToolRepository<T, Long> repository,
                               final AbstractToolCriteria<T, Long, S> criteria,
                               final SecurityContextHelper securityContextHelper) {
        this.tool = tool;
        this.searchClass = searchClass;
        this.buildProperties = buildProperties;
        this.environment = environment;
        this.repository = repository;
        this.criteria = criteria;
        this.securityContextHelper = securityContextHelper;
    }

    public Page<T> findAll(final S search, final Pageable pageable) {
        return criteria.findAll(search, pageable);
    }

    public T get(final long id) {
        T element = this.repository.findById(id).orElseThrow(() -> new GeoPocketException(String.format("Entity %s not found", id)));
        checkPermissions(element.getProject());
        return element;
    }

    public void delete(final T element) {
        checkPermissions(element.getProject());
        this.repository.delete(element);
    }

    protected CalculationInfo buildCalculationInfo() {
        final CalculationInfo calculationInfo = new CalculationInfo();
        calculationInfo.setBuildVersion(buildProperties.getVersion());
        calculationInfo.setBuildGroup(buildProperties.getGroup());
        calculationInfo.setBuildArtifact(buildProperties.getArtifact());
        calculationInfo.setBuildTime(buildProperties.getTime());
        calculationInfo.setActiveProfiles(String.join(",", Arrays.asList(environment.getActiveProfiles())));
        return calculationInfo;
    }

    public Long countAll() {
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            return repository.count();
        }
        return repository.countByAuditCreatedBy(securityContextHelper.getUserName());
    }

    public List<Usage> countUsages(final Long projectId, final Status status) {
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            final Set<String> users = repository.findDistinctUsers(projectId);
            final List<Usage> usages = Optional.ofNullable(users).orElse(Collections.emptySet()).stream()
                    .map(user -> Usage.builder()
                            .user(user)
                            .usage(repository.countByProjectIdAndStatusAndAuditCreatedBy(projectId, status, user))
                            .build())
                    .collect(Collectors.toList());
            final Usage usage = Optional.of(usages).orElse(Collections.emptyList()).stream()
                    .filter(u -> u.getUser().equals(securityContextHelper.getUserName())).findFirst().orElse(null);
            if (Objects.isNull(usage)) {
                usages.add(Usage.builder().user(securityContextHelper.getUserName()).usage(0L).build());
            }
            return usages;
        }
        final ArrayList<Usage> usages = new ArrayList<>();
        usages.add(Usage.builder()
                .user(securityContextHelper.getUserName())
                .usage(repository.countByProjectIdAndStatusAndAuditCreatedBy(projectId, status, securityContextHelper.getUserName()))
                .build());
        return usages;
    }

    public List<Summary> getSummaries(Long projectId) {
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            return Optional.ofNullable(repository.findAllByProjectId(projectId)).orElse(Collections.emptyList()).stream()
                    .map(entity -> Summary.builder().tool(tool).id(entity.getId()).audit(entity.getAudit())
                            .projectId(projectId).status(entity.getStatus()).build())
                    .collect(Collectors.toList());
        }
        return Optional.ofNullable(repository.findAllByProjectIdAndAuditCreatedBy(projectId, securityContextHelper.getUserName()))
                .orElse(Collections.emptyList()).stream().map(entity -> Summary.builder().tool(tool).id(entity.getId())
                        .audit(entity.getAudit()).projectId(projectId).status(entity.getStatus()).build())
                .collect(Collectors.toList());
    }

    public Long count(final Status status) {
        if (securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)) {
            return repository.countByStatus(status);
        }
        return repository.countByStatusAndAuditCreatedBy(status, securityContextHelper.getUserName());
    }

    public List<Summary> getCurrentSummaries(int amount) {
        final S search;
        try {
            search = searchClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error creating a new search instance");
            return Collections.emptyList();
        }
        final Pageable pageable = PageRequest.of(0, amount, Sort.by(Sort.Order.desc("audit.updatedOn")));
        final Page<T> page = criteria.findAll(search, pageable);
        return Optional.of(page.getContent()).orElse(Collections.emptyList()).stream().map(entity ->
                Summary.builder().tool(tool).id(entity.getId()).audit(entity.getAudit())
                        .projectId(entity.getProject().getId()).status(entity.getStatus()).build())
                .collect(Collectors.toList());
    }

    private void checkPermissions(GeoPocketProjectEntity project) {
        if (!securityContextHelper.hasRole(GeoPocketRole.ROLE_PROFESSOR)
                && !project.getUser().equals(securityContextHelper.getUserName())) {
            throw new GeoPocketException("Different project owner");
        }
    }

}
