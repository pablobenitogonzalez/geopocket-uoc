package edu.uoc.geopocket.berock.services;

import edu.uoc.geopocket.berock.calculation.BerockExecutor;
import edu.uoc.geopocket.berock.common.BerockSearch;
import edu.uoc.geopocket.berock.criterias.BerockCriteria;
import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.entities.Joint;
import edu.uoc.geopocket.berock.repositories.BerockRepository;
import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.AbstractToolService;
import edu.uoc.geopocket.common.services.StatisticsService;
import edu.uoc.geopocket.common.services.ToolService;
import edu.uoc.geopocket.project.entities.Project;
import edu.uoc.geopocket.project.services.ProjectService;
import edu.uoc.geopocket.security.helper.SecurityContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class BerockServiceImpl extends AbstractToolService<Berock, BerockSearch>
        implements ToolService<Berock, BerockSearch>, StatisticsService, BerockService {

    private ProjectService projectService;

    private BerockExecutor executor;

    @Autowired
    public BerockServiceImpl(final BuildProperties buildProperties, final Environment environment,
                             final BerockRepository repository, final BerockCriteria criteria,
                             final ProjectService projectService, final BerockExecutor executor,
                             final SecurityContextHelper securityContextHelper) {
        super(Tool.BEROCK, BerockSearch.class, buildProperties, environment, repository, criteria, securityContextHelper);
        this.projectService = projectService;
        this.executor = executor;
    }

    @Override
    public Berock saveDraft(final Berock berock, final Long projectId) {
        if (Objects.isNull(berock.getId())) {
            final Project project = projectService.get(projectId);
            berock.setProject(project);
            berock.setStatus(Status.DRAFT);
            return repository.save(berock);
        }
        final Berock berockDBO = get(berock.getId());
        if (!Status.DRAFT.equals(berockDBO.getStatus())) {
            throw new GeoPocketException(String.format("Cannot save berock in status %s", berockDBO.getStatus()));
        }
        berockDBO.getJoints().clear();
        final Project project = projectService.get(projectId);
        berockDBO.setProject(project);
        berockDBO.setRockType(berock.getRockType());
        berockDBO.setWeatheringDegree(berock.getWeatheringDegree());
        berockDBO.setRockQualityDesignation(berock.getRockQualityDesignation());
        berockDBO.getJoints().addAll(berock.getJoints());
        berockDBO.getAudit().preUpdate(); // issue with transactions
        return repository.save(berockDBO);
    }

    @Override
    @Transactional
    public Berock calculate(final Berock berock, final Long projectId) {
        final Berock berockDBO = saveDraft(berock, projectId);
        berockDBO.setCalculationInfo(buildCalculationInfo());
        executor.calculate(berockDBO);
        berockDBO.setStatus(Status.CALCULATED);
        return repository.save(berockDBO);
    }

    @Override
    public Berock clone(final Long id) {
        final Berock berockDBO = get(id);
        final Berock berock = new Berock();
        berock.setRockType(berockDBO.getRockType());
        berock.setWeatheringDegree(berockDBO.getWeatheringDegree());
        berock.setRockQualityDesignation(berockDBO.getRockQualityDesignation());
        berock.setJoints(new ArrayList<>());
        Optional.ofNullable(berockDBO.getJoints()).orElse(Collections.emptyList()).forEach(jointDBO -> {
            final Joint joint = new Joint();
            joint.setSpacing(jointDBO.getSpacing());
            joint.setFamily(jointDBO.getFamily());
            berock.getJoints().add(joint);
        });
        return saveDraft(berock, berockDBO.getProject().getId());
    }

}
