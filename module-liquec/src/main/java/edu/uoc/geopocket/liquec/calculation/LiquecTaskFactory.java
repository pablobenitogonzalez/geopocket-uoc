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
public class LiquecTaskFactory {

    private final ApplicationContext applicationContext;

    private static final Map<LiquecTask, Class<? extends AbstractLiquecTaskExecutable>> FACTORY = new EnumMap<>(LiquecTask.class);

    static {
        FACTORY.put(LiquecTask.CHECK_DEPTH_ABOVE_GWT, CheckDepthAboveGwtLiquecTask.class);
        FACTORY.put(LiquecTask.CHECK_MAX_DEPTH, CheckMaxDepthLiquecTask.class);
        FACTORY.put(LiquecTask.CHECK_LIQUEFACTION_OPTION, CheckLiquefactionOptionLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_TOTAL_STRESS, TotalStressLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_INTERSTITIAL_STRESS, InterstitialStressLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EFFECTIVE_STRESS, EffectiveStressLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EFFECTIVE_STRESS_FACTOR, EffectiveStressFactorLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_SPT_CORRECTION, SptCorrectionLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO, CycleResistanceRatioLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_COEFFICIENT_CONTRIBUTION_CORRECTION, CoefficientContributionCorrectionLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION, EarthquakeMagnitudeCorrectionLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION, CycleResistanceRatioCorrectionLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_DEPTH_FACTOR, DepthFactorLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_CYCLE_STRESS_RATIO, CycleStressRatioLiquecTask.class);
        FACTORY.put(LiquecTask.CALCULATE_SAFETY_FACTOR, SafetyFactorLiquecTask.class);
    }

    @Autowired
    public LiquecTaskFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    AbstractLiquecTaskExecutable getExecutableTask(final LiquecTask task) {
        return Optional.of(applicationContext.getBean(LiquecTaskFactory.FACTORY.get(task)))
                .orElseThrow(() -> new GeoPocketException(String.format("Executable task not found for [%s]", task.getDescription())));
    }

}
