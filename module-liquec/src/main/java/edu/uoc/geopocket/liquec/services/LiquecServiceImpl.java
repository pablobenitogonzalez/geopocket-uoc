package edu.uoc.geopocket.liquec.services;

import edu.uoc.geopocket.common.Status;
import edu.uoc.geopocket.common.Tool;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.common.services.AbstractToolService;
import edu.uoc.geopocket.common.services.StatisticsService;
import edu.uoc.geopocket.common.services.ToolService;
import edu.uoc.geopocket.liquec.calculation.LiquecExecutor;
import edu.uoc.geopocket.liquec.common.LiquecSearch;
import edu.uoc.geopocket.liquec.criterias.LiquecCriteria;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.SoilLayer;
import edu.uoc.geopocket.liquec.entities.Spt;
import edu.uoc.geopocket.liquec.repositories.LiquecRepository;
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
public class LiquecServiceImpl extends AbstractToolService<Liquec, LiquecSearch>
        implements ToolService<Liquec, LiquecSearch>, StatisticsService, LiquecService {

    private ProjectService projectService;

    private LiquecExecutor executor;

    @Autowired
    public LiquecServiceImpl(final BuildProperties buildProperties, final Environment environment,
                             final LiquecRepository repository, final LiquecCriteria criteria,
                             final ProjectService projectService, final LiquecExecutor executor,
                             final SecurityContextHelper securityContextHelper) {
        super(Tool.LIQUEC, LiquecSearch.class, buildProperties, environment, repository, criteria, securityContextHelper);
        this.projectService = projectService;
        this.executor = executor;
    }

    @Override
    public Liquec saveDraft(final Liquec liquec, final Long projectId) {
        if (Objects.isNull(liquec.getId())) {
            final Project project = projectService.get(projectId);
            liquec.setProject(project);
            liquec.setStatus(Status.DRAFT);
            return repository.save(liquec);
        }
        final Liquec liquecDBO = get(liquec.getId());
        if (!Status.DRAFT.equals(liquecDBO.getStatus())) {
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

    @Override
    @Transactional
    public Liquec calculate(final Liquec liquec, final Long projectId) {
        final Liquec liquecDBO = saveDraft(liquec, projectId);
        liquecDBO.setCalculationInfo(buildCalculationInfo());
        executor.calculate(liquecDBO);
        liquecDBO.setStatus(Status.CALCULATED);
        return repository.save(liquecDBO);
    }

    @Override
    public Liquec clone(final Long id) {
        final Liquec liquecDBO = get(id);
        final Liquec liquec = new Liquec();
        liquec.setCode(liquecDBO.getCode());
        liquec.setPeakGroundAcceleration(liquecDBO.getPeakGroundAcceleration());
        liquec.setEarthquakeMagnitude(liquecDBO.getEarthquakeMagnitude());
        liquec.setCoefficientOfContribution(liquecDBO.getCoefficientOfContribution());
        liquec.setGroundWaterTableDepth(liquecDBO.getGroundWaterTableDepth());
        liquec.setSoilLayers(new ArrayList<>());
        Optional.ofNullable(liquecDBO.getSoilLayers()).orElse(Collections.emptyList()).forEach(soilLayerDBO -> {
            final SoilLayer soilLayer = new SoilLayer();
            soilLayer.setStartDepth(soilLayerDBO.getStartDepth());
            soilLayer.setFinalDepth(soilLayerDBO.getFinalDepth());
            soilLayer.setSoilType(soilLayerDBO.getSoilType());
            soilLayer.setAboveGwt(soilLayerDBO.getAboveGwt());
            soilLayer.setBelowGwt(soilLayerDBO.getBelowGwt());
            soilLayer.setFinesContent(soilLayerDBO.getFinesContent());
            soilLayer.setCheckLiquefaction(soilLayerDBO.getCheckLiquefaction());
            liquec.getSoilLayers().add(soilLayer);
        });
        liquec.setSpts(new ArrayList<>());
        Optional.ofNullable(liquecDBO.getSpts()).orElse(Collections.emptyList()).forEach(sptDBO -> {
            final Spt spt = new Spt();
            spt.setDepth(sptDBO.getDepth());
            spt.setSptBlowCounts(sptDBO.getSptBlowCounts());
            spt.setEnergyRatio(sptDBO.getEnergyRatio());
            liquec.getSpts().add(spt);
        });
        return saveDraft(liquec, liquecDBO.getProject().getId());
    }

}
