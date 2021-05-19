package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CrrRangeFactory {

    private final ApplicationContext applicationContext;

    private static final Map<Crr, Class<? extends LiquecRange>> FACTORY = new EnumMap<>(Crr.class);

    static {
        FACTORY.put(Crr.LESS_OR_EQUAL, LessOrEqual.class);
        FACTORY.put(Crr.BETWEEN_FIRST_SECTION, BetweenFirstSection.class);
        FACTORY.put(Crr.BETWEEN_SECOND_SECTION, BetweenSecondSection.class);
        FACTORY.put(Crr.GREATER_OR_EQUAL, GreaterOrEqual.class);
    }

    @Autowired
    public CrrRangeFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public LiquecRange getCrrEvaluation(final Crr crr) {
        return Optional.of(applicationContext.getBean(CrrRangeFactory.FACTORY.get(crr)))
                .orElseThrow(() -> new GeoPocketException(String.format("Crr evaluation not found for [%s]", crr.getDescription())));
    }
}
