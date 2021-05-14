package edu.uoc.geopocket.berock.calculation;

import edu.uoc.geopocket.berock.calculation.task.*;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class BerockTaskFactory {

    private final ApplicationContext applicationContext;

    private static final Map<BerockTask, Class<? extends AbstractBerockTaskExecutable>> FACTORY = new EnumMap<>(BerockTask.class);

    static {
        FACTORY.put(BerockTask.CALCULATE_ALPHA_3A, Alpha3aTask.class);
        FACTORY.put(BerockTask.CALCULATE_ALPHA_3B, Alpha3bTask.class);
        FACTORY.put(BerockTask.CALCULATE_JV, JvTask.class);
        FACTORY.put(BerockTask.CALCULATE_RDQ, RdqTask.class);
        FACTORY.put(BerockTask.CALCULATE_FINAL_ALPHA_3, FinalAlpha3Task.class);
        FACTORY.put(BerockTask.CALCULATE_ALLOWABLE_BEARING_PRESSURE, AllowableBearingPressureTask.class);
    }

    @Autowired
    public BerockTaskFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    AbstractBerockTaskExecutable getExecutableTask(final BerockTask task) {
        return Optional.of(applicationContext.getBean(BerockTaskFactory.FACTORY.get(task)))
                .orElseThrow(() -> new GeoPocketException(String.format("Executable task not found for [%s]", task.getDescription())));
    }

}
