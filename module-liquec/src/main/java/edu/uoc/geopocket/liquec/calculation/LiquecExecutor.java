package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import edu.uoc.geopocket.liquec.entities.Liquec;
import edu.uoc.geopocket.liquec.entities.SoilLayer;
import edu.uoc.geopocket.liquec.entities.Spt;
import edu.uoc.geopocket.liquec.entities.SptResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LiquecExecutor {

    private RoadMapTaskFactory roadMapFactory;

    private TaskFactory taskFactory;

    @Autowired
    public LiquecExecutor(final RoadMapTaskFactory roadMapFactory, final TaskFactory taskFactory) {
        this.roadMapFactory = roadMapFactory;
        this.taskFactory = taskFactory;
    }

    public void calculate(final Liquec liquecProject) {

        this.checkCalculationData(liquecProject);

        final long start = System.nanoTime();

        log.info("START LIQUEC CALCULATION");

        for (Spt spt : liquecProject.getSpts()) {
            this.logStart(spt);
            spt.setSptResult(new SptResult());
            final RoadMap roadMap = roadMapFactory.getRoadMap(liquecProject.getCode());
            for (Object task : roadMap.getSteps()) {
                try {
                    final AbstractTaskExecutable executableTask = this.taskFactory.getExecutableTask((LiquecTask) task);
                    log.info(String.format("%s - Start task - %s",
                            liquecProject.getCode().getDescription(), executableTask.getTask().getDescription()));
                    executableTask.execute(liquecProject, spt);
                    spt.getSptResult().setResult(Result.OK);
                    log.info(String.format("%s - End task - %s with result %s",
                        liquecProject.getCode().getDescription(), executableTask.getTask().getDescription(), Result.OK));
                } catch (LiquecTaskException e) {
                    final String message = e.getReasonMessage();
                    spt.getSptResult().setResult(e.getResult());
                    spt.getSptResult().setMessage(message);
                    log.info(String.format("%s - End task - %s with result %s and reason <%s>",
                            liquecProject.getCode().getDescription(), e.getTask().getDescription(), e.getResult(), message));
                    break;
                }
            }

            this.logEnd(spt);
        }

        log.info("END LIQUEC CALCULATION");

        log.info(String.format("Calculation took %s", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start)));
    }

    private void checkCalculationData(final Liquec liquecProject) {
        if (liquecProject == null) {
            throw new GeoPocketException("Project required");
        }
        if (liquecProject.getCode() == null) {
            throw new GeoPocketException("Calculation code required");
        }
        if (liquecProject.getPeakGroundAcceleration() == null) {
            throw new GeoPocketException("Peak ground acceleration required");
        }
        if (liquecProject.getCode().equals(LiquecCode.EUROCODE) && liquecProject.getEarthquakeMagnitude() == null) {
            throw new GeoPocketException("Earthquake magnitude required");
        }
        if (liquecProject.getCode().equals(LiquecCode.NCSE_02) && liquecProject.getCoefficientOfContribution() == null) {
            throw new GeoPocketException("Coefficient of contribution required");
        }
        if (liquecProject.getGroundWaterTableDepth() == null) {
            throw new GeoPocketException("Ground water table depth required");
        }
        if (liquecProject.getSoilLayers().size() == 0) {
            throw new GeoPocketException("Soil layers list required");
        }
        for (SoilLayer soilLayer : liquecProject.getSoilLayers()) {
            if (soilLayer.getStartDepth() == null) {
                throw new GeoPocketException("Start depth required");
            }
            if (soilLayer.getFinalDepth() == null) {
                throw new GeoPocketException("Final depth required");
            }
            if (soilLayer.getAboveGwt() == null) {
                throw new GeoPocketException("Above GWT required");
            }
            if (soilLayer.getBelowGwt() == null) {
                throw new GeoPocketException("Below GWT required");
            }
            if (soilLayer.getFinesContent() == null) {
                throw new GeoPocketException("EvaluableFines content required");
            }
        }
        if (liquecProject.getSpts().size() == 0) {
            throw new GeoPocketException("Standard penetration test list required");
        }
        for (Spt spt : liquecProject.getSpts()) {
            if (spt.getDepth() == null) {
                throw new GeoPocketException("SPT depth required");
            }
            if (spt.getSptBlowCounts() == null) {
                throw new GeoPocketException("SPT blow counts required");
            }
            if (spt.getEnergyRatio() == null) {
                throw new GeoPocketException("Energy ratio required");
            }
        }
    }

    private void logMark() {
        log.info("================================================================ ");
    }

    private void logSpt(final Spt spt) {
        log.info("START - Depth: " + spt.getDepth() + " (m) - SPT Blow Counts: " + spt.getSptBlowCounts() + " (N)");
    }

    private void logStart(final Spt spt) {
        this.logMark();
        this.logSpt(spt);
    }

    private void logEnd(final Spt spt) {
        this.logSpt(spt);
        this.logMark();
    }
}
