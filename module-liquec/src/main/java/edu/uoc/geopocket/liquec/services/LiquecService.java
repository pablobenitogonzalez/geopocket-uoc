package edu.uoc.geopocket.liquec.services;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.AbstractToolService;
import edu.uoc.geopocket.liquec.calculation.LiquecExecutor;
import edu.uoc.geopocket.liquec.common.LiquecStatus;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.repositories.LiquecRepository;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class LiquecService extends AbstractToolService<Liquec> {

    private LiquecRepository repository;

    private ProjectService projectService;

    private LiquecExecutor executor;

    @Autowired
    public LiquecService(final LiquecRepository repository, final ProjectService projectService, final LiquecExecutor executor) {
        this.repository = repository;
        this.projectService = projectService;
        this.executor = executor;
    }

    @Override
    public LiquecRepository getRepository() {
        return this.repository;
    }

    @Override
    public Page<Liquec> findAllByProject(final Long projectId, final Pageable pageable) {
        return repository.findAllByProject(projectId, pageable);
    }

    @Override
    @Transactional
    public Liquec saveDraft(final Liquec liquec, final Long projectId) {
        return save(liquec, projectId, LiquecStatus.DRAFT);
    }

    @Override
    @Transactional
    public Liquec calculate(final Liquec liquec, final Long projectId) {
        final Liquec liquecDBO = save(liquec, projectId, LiquecStatus.CALCULATING);
        executor.calculate(liquecDBO);
        liquecDBO.setStatus(LiquecStatus.CALCULATED);
        return repository.save(liquecDBO);
    }

    private Liquec save(final Liquec liquec, final Long projectId, final LiquecStatus status) {
        if (Objects.isNull(liquec.getId())) {
            final Project project = projectService.get(projectId);
            liquec.setProject(project);
            liquec.setStatus(status);
            return repository.save(liquec);
        }
        final Liquec liquecDBO = get(liquec.getId());
        if (!LiquecStatus.DRAFT.equals(liquecDBO.getStatus())) {
            throw new GeoPocketException(String.format("Cannot save liquec in status %s", liquecDBO.getStatus()));
        }
        liquecDBO.getSoilLayers().clear();
        liquecDBO.getSpts().clear();
        final Project project = projectService.get(projectId);
        liquecDBO.setProject(project);
        liquecDBO.setCode(liquec.getCode());
        liquecDBO.setPeakGroundAcceleration(liquec.getPeakGroundAcceleration());
        liquecDBO.setEarthquakeMagnitude(liquec.getEarthquakeMagnitude());
        liquecDBO.setCoefficientOfContribution(liquec.getCoefficientOfContribution());
        liquecDBO.setGroundWaterTableDepth(liquec.getGroundWaterTableDepth());
        liquecDBO.getSoilLayers().addAll(liquec.getSoilLayers());
        liquecDBO.getSpts().addAll(liquec.getSpts());
        liquecDBO.getAudit().preUpdate(); // issue with transactions
        return repository.save(liquecDBO);
    }

}
