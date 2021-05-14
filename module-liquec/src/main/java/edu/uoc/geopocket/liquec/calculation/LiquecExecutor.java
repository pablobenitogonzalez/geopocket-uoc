package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.calculation.Describable;
import edu.uoc.geopocket.common.calculation.RoadMap;
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

    private LiquecRoadMapTaskFactory roadMapFactory;

    private LiquecTaskFactory liquecTaskFactory;

    @Autowired
    public LiquecExecutor(final LiquecRoadMapTaskFactory roadMapFactory, final LiquecTaskFactory liquecTaskFactory) {
        this.roadMapFactory = roadMapFactory;
        this.liquecTaskFactory = liquecTaskFactory;
    }

    public void calculate(final Liquec liquec) {

        this.checkCalculationData(liquec);

        final long start = System.nanoTime();

        log.info("START LIQUEC CALCULATION");

        for (Spt spt : liquec.getSpts()) {
            this.logStart(spt);
            spt.setSptResult(new SptResult());
            final RoadMap<? extends Describable> roadMap = roadMapFactory.getRoadMap(liquec.getCode());
            for (Object task : roadMap.getSteps()) {
                try {
                    final AbstractLiquecTaskExecutable executableTask = this.liquecTaskFactory.getExecutableTask((LiquecTask) task);
                    log.info(String.format("%s - Start task - %s",
                            liquec.getCode().getName(), executableTask.getTask().getDescription()));
                    executableTask.execute(liquec, spt);
                    spt.getSptResult().setResult(Result.OK);
                    log.info(String.format("%s - End task - %s with result %s",
                        liquec.getCode().getName(), executableTask.getTask().getDescription(), Result.OK));
                } catch (LiquecTaskException e) {
                    final String message = e.getReasonMessage();
                    spt.getSptResult().setResult(e.getResult());
                    spt.getSptResult().setMessage(message);
                    log.info(String.format("%s - End task - %s with result %s and reason <%s>",
                            liquec.getCode().getName(), e.getTask().getDescription(), e.getResult(), message));
                    break;
                }
            }

            this.logEnd(spt);
        }

        log.info("END LIQUEC CALCULATION");

        final long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        liquec.getCalculationInfo().setElapsedTime(elapsedTime);

        log.info(String.format("Calculation took %s", elapsedTime));
    }

    private void checkCalculationData(final Liquec liquec) {
        if (liquec == null) {
            throw new GeoPocketException("Liquec required");
        }
        if (liquec.getCode() == null) {
            throw new GeoPocketException("Calculation code required");
        }
        if (liquec.getPeakGroundAcceleration() == null) {
            throw new GeoPocketException("Peak ground acceleration required");
        }
        if (liquec.getCode().equals(LiquecCode.EUROCODE) && liquec.getEarthquakeMagnitude() == null) {
            throw new GeoPocketException("Earthquake magnitude required");
        }
        if (liquec.getCode().equals(LiquecCode.NCSE_02) && liquec.getCoefficientOfContribution() == null) {
            throw new GeoPocketException("Coefficient of contribution required");
        }
        if (liquec.getGroundWaterTableDepth() == null) {
            throw new GeoPocketException("Ground water table depth required");
        }
        if (liquec.getSoilLayers().size() == 0) {
            throw new GeoPocketException("Soil layers list required");
        }
        for (SoilLayer soilLayer : liquec.getSoilLayers()) {
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
        if (liquec.getSpts().size() == 0) {
            throw new GeoPocketException("Standard penetration test list required");
        }
        for (Spt spt : liquec.getSpts()) {
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
