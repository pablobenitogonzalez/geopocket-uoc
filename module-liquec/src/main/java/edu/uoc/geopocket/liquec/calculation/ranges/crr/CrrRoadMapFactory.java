package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.calculation.RoadMap;
import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.common.LiquecCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CrrRoadMapFactory {

    private final ApplicationContext applicationContext;

    private static final Map<Crr, List<Class<? extends RoadMap<?>>>> EUROCODE_FACTORY = new EnumMap<>(Crr.class);
    static {
        EUROCODE_FACTORY.put(Crr.LESS_OR_EQUAL, Arrays.asList(EurocodeFines05RoadMap.class, null));
        EUROCODE_FACTORY.put(Crr.BETWEEN_FIRST_SECTION, Arrays.asList(EurocodeFines15RoadMap.class, EurocodeFines05RoadMap.class));
        EUROCODE_FACTORY.put(Crr.BETWEEN_SECOND_SECTION, Arrays.asList(EurocodeFines35RoadMap.class, EurocodeFines15RoadMap.class));
        EUROCODE_FACTORY.put(Crr.GREATER_OR_EQUAL, Arrays.asList(EurocodeFines35RoadMap.class, null));
    }

    private static final Map<Crr, List<Class<? extends RoadMap<?>>>> NCSE02_FACTORY = new EnumMap<>(Crr.class);
    static {
        NCSE02_FACTORY.put(Crr.LESS_OR_EQUAL, Arrays.asList(Ncse02Fines05RoadMap.class, null));
        NCSE02_FACTORY.put(Crr.BETWEEN_FIRST_SECTION, Arrays.asList(Ncse02Fines15RoadMap.class, Ncse02Fines05RoadMap.class));
        NCSE02_FACTORY.put(Crr.BETWEEN_SECOND_SECTION, Arrays.asList(Ncse02Fines35RoadMap.class, Ncse02Fines15RoadMap.class));
        NCSE02_FACTORY.put(Crr.GREATER_OR_EQUAL, Arrays.asList(Ncse02Fines35RoadMap.class, null));
    }

    @Autowired
    public CrrRoadMapFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public RoadMap<?> getRoadMap(final Crr crr, final LiquecCode code, final int index) {
        return Optional.of(applicationContext.getBean(this.getFactory(code).get(crr).get(index)))
            .orElseThrow(() -> new GeoPocketException(String.format("Road map not found for CRR [%s], code [%s] and and index [%s]",
                        crr.getDescription(), code, index)));
    }

    private Map<Crr, List<Class<? extends RoadMap<?>>>> getFactory(final LiquecCode code) {
        if (LiquecCode.EUROCODE.equals(code)) {
            return EUROCODE_FACTORY;
        }
        return NCSE02_FACTORY;
    }
}
