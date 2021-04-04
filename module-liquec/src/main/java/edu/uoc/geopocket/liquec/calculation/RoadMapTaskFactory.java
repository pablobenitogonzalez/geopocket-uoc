package edu.uoc.geopocket.liquec.calculation;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RoadMapTaskFactory {

    private final ApplicationContext applicationContext;

    private static final Map<LiquecCode, Class<? extends RoadMap>> FACTORY = new EnumMap<>(LiquecCode.class);

    static {
        FACTORY.put(LiquecCode.EUROCODE, EurocodeRoadMap.class);
        FACTORY.put(LiquecCode.NCSE_02, Ncse02RoadMap.class);
    }

    @Autowired
    public RoadMapTaskFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    RoadMap getRoadMap(final LiquecCode code) {
        return Optional.ofNullable(applicationContext.getBean(RoadMapTaskFactory.FACTORY.get(code)))
            .orElseThrow(() -> new GeoPocketException(String.format("Road map task not found for [%s]", code.getDescription())));
    }

}
