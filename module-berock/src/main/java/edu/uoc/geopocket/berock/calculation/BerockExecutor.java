package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.berock.entities.Berock;
import edu.uoc.geopocket.berock.entities.BerockResult;
import edu.uoc.geopocket.berock.entities.Joint;
import edu.uoc.geopocket.common.Result;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BerockExecutor {

    private final BerockRoadMap berockRoadMap;

    private final BerockTaskFactory berockTaskFactory;

    @Autowired
    public BerockExecutor(final BerockRoadMap berockRoadMap, final BerockTaskFactory berockTaskFactory) {
        this.berockRoadMap = berockRoadMap;
        this.berockTaskFactory = berockTaskFactory;
    }

    public void calculate(final Berock berock) {

        this.checkCalculationData(berock);

        final long start = System.nanoTime();

        log.info("START BEROCK CALCULATION");

        berock.setResult(new BerockResult());

        for (Object task : berockRoadMap.getSteps()) {
            try {
                final AbstractBerockTaskExecutable executableTask = this.berockTaskFactory.getExecutableTask((BerockTask) task);
                log.info(String.format("Start task - %s", executableTask.getTask().getDescription()));
                executableTask.execute(berock);
                berock.getResult().setResult(Result.OK);
                log.info(String.format("End task - %s with result %s", executableTask.getTask().getDescription(), Result.OK));
            } catch (BerockTaskException e) {
                final String message = e.getReasonMessage();
                berock.getResult().setResult(e.getResult());
                berock.getResult().setMessage(message);
                log.info(String.format("End task - %s with result %s and reason <%s>", e.getTask().getDescription(), e.getResult(), message));
                break;
            }
        }

        log.info("END BEROCK CALCULATION");

        final long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        berock.getCalculationInfo().setElapsedTime(elapsedTime);

        log.info(String.format("Calculation took %s", elapsedTime));
    }

    private void checkCalculationData(final Berock berock) {
        if (Objects.isNull(berock)) {
            throw new GeoPocketException("Berock required");
        }
        if (Objects.isNull(berock.getRockType())) {
            throw new GeoPocketException("Rock type required");
        }
        if (Objects.isNull(berock.getWeatheringDegree())) {
            throw new GeoPocketException("Weathering degree required");
        }
        if (Objects.isNull(berock.getJoints()) || berock.getJoints().isEmpty()) {
            throw new GeoPocketException("Joints list required");
        }
        for (Joint joint : berock.getJoints()) {
            if (Objects.isNull(joint.getSpacing())) {
                throw new GeoPocketException("Joint spacing required");
            }
        }
    }
}
