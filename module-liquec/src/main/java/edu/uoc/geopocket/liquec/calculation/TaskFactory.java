package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.calculation.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TaskFactory {

    private final ApplicationContext applicationContext;

    private static final Map<LiquecTask, Class<? extends AbstractTaskExecutable>> FACTORY = new EnumMap<>(LiquecTask.class);

    static {
        FACTORY.put(LiquecTask.CHECK_DEPTH_ABOVE_GWT, CheckDepthAboveGwtTask.class);
        FACTORY.put(LiquecTask.CHECK_MAX_DEPTH, CheckMaxDepthTask.class);
        FACTORY.put(LiquecTask.CHECK_LIQUEFACTION_OPTION, CheckLiquefactionOptionTask.class);
        FACTORY.put(LiquecTask.CALCULATE_TOTAL_STRESS, TotalStressTask.class);
        FACTORY.put(LiquecTask.CALCULATE_INTERSTITIAL_STRESS, InterstitialStressTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EFFECTIVE_STRESS, EffectiveStressTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EFFECTIVE_STRESS_FACTOR, EffectiveStressFactorTask.class);
        FACTORY.put(LiquecTask.CALCULATE_SPT_CORRECTION, SptCorrectionTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO, CycleResistanceRatioTask.class);
        FACTORY.put(LiquecTask.CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION, CoefficientContributionCorrectionTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION, EarthquakeMagnitudeCorrectionTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION, CycleResistanceRatioCorrectionTask.class);
        FACTORY.put(LiquecTask.CALCULATE_DEPTH_FACTOR, DepthFactorTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_STRESS_RATIO, CycleStressRatioTask.class);
        FACTORY.put(LiquecTask.CALCULATE_SAFETY_FACTOR, SafetyFactorTask.class);
    }

    @Autowired
    public TaskFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    AbstractTaskExecutable getExecutableTask(final LiquecTask task) {
        return Optional.ofNullable(applicationContext.getBean(TaskFactory.FACTORY.get(task)))
                .orElseThrow(() -> new GeoPocketException(String.format("Executable task not found for [%s]", task.getDescription())));
    }

}
