package edu.uoc.geopocket.liquec.calculation.ranges.crr;

import edu.uoc.geopocket.common.exceptions.GeoPocketException;
import edu.uoc.geopocket.liquec.calculation.LiquecRange;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05.EurocodeBetweenFirstSection05;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05.EurocodeBetweenSecondSection05;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05.EurocodeGreaterOrEqual05;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines05.EurocodeLessOrEqual05;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines15.EurocodeBetweenFirstSection15;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines15.EurocodeBetweenSecondSection15;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines15.EurocodeGreaterOrEqual15;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines15.EurocodeLessOrEqual15;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines35.EurocodeBetweenFirstSection35;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines35.EurocodeBetweenSecondSection35;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines35.EurocodeGreaterOrEqual35;
import edu.uoc.geopocket.liquec.calculation.ranges.eurocode.fines35.EurocodeLessOrEqual35;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines05.Ncse02Between05;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines05.Ncse02GreaterOrEqual05;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines05.Ncse02LessOrEqual05;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines15.Ncse02Between15;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines15.Ncse02GreaterOrEqual15;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines15.Ncse02LessOrEqual15;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines35.Ncse02Between35;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines35.Ncse02GreaterOrEqual35;
import edu.uoc.geopocket.liquec.calculation.ranges.ncse.fines35.Ncse02LessOrEqual35;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
public class EvaluableFinesFactory {

    private final ApplicationContext applicationContext;

    private static final Map<EvaluableFines, Class<? extends LiquecRange>> FACTORY = new EnumMap<>(EvaluableFines.class);
    static {
        FACTORY.put(EvaluableFines.EUROCODE_F05_LESS_OR_EQUAL, EurocodeLessOrEqual05.class);
        FACTORY.put(EvaluableFines.EUROCODE_F05_BETWEEN_FIRST_SECTION, EurocodeBetweenFirstSection05.class);
        FACTORY.put(EvaluableFines.EUROCODE_F05_BETWEEN_SECOND_SECTION, EurocodeBetweenSecondSection05.class);
        FACTORY.put(EvaluableFines.EUROCODE_F05_GREATER_OR_EQUAL, EurocodeGreaterOrEqual05.class);
        FACTORY.put(EvaluableFines.EUROCODE_F15_LESS_OR_EQUAL, EurocodeLessOrEqual15.class);
        FACTORY.put(EvaluableFines.EUROCODE_F15_BETWEEN_FIRST_SECTION, EurocodeBetweenFirstSection15.class);
        FACTORY.put(EvaluableFines.EUROCODE_F15_BETWEEN_SECOND_SECTION, EurocodeBetweenSecondSection15.class);
        FACTORY.put(EvaluableFines.EUROCODE_F15_GREATER_OR_EQUAL, EurocodeGreaterOrEqual15.class);
        FACTORY.put(EvaluableFines.EUROCODE_F35_LESS_OR_EQUAL, EurocodeLessOrEqual35.class);
        FACTORY.put(EvaluableFines.EUROCODE_F35_BETWEEN_FIRST_SECTION, EurocodeBetweenFirstSection35.class);
        FACTORY.put(EvaluableFines.EUROCODE_F35_BETWEEN_SECOND_SECTION, EurocodeBetweenSecondSection35.class);
        FACTORY.put(EvaluableFines.EUROCODE_F35_GREATER_OR_EQUAL, EurocodeGreaterOrEqual35.class);
        FACTORY.put(EvaluableFines.NCSE02_F05_LESS_OR_EQUAL, Ncse02LessOrEqual05.class);
        FACTORY.put(EvaluableFines.NCSE02_F05_BETWEEN, Ncse02Between05.class);
        FACTORY.put(EvaluableFines.NCSE02_F05_GREATER_OR_EQUAL, Ncse02GreaterOrEqual05.class);
        FACTORY.put(EvaluableFines.NCSE02_F15_LESS_OR_EQUAL, Ncse02LessOrEqual15.class);
        FACTORY.put(EvaluableFines.NCSE02_F15_BETWEEN, Ncse02Between15.class);
        FACTORY.put(EvaluableFines.NCSE02_F15_GREATER_OR_EQUAL, Ncse02GreaterOrEqual15.class);
        FACTORY.put(EvaluableFines.NCSE02_F35_LESS_OR_EQUAL, Ncse02LessOrEqual35.class);
        FACTORY.put(EvaluableFines.NCSE02_F35_BETWEEN, Ncse02Between35.class);
        FACTORY.put(EvaluableFines.NCSE02_F35_GREATER_OR_EQUAL, Ncse02GreaterOrEqual35.class);
    }

    @Autowired
    public EvaluableFinesFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public LiquecRange getRange(final EvaluableFines evaluableFines) {
        return Optional.ofNullable(applicationContext.getBean(EvaluableFinesFactory.FACTORY.get(evaluableFines)))
                .orElseThrow(() -> new GeoPocketException(String.format("Evaluable fines not found for [%s]", evaluableFines.getDescription())));
    }
}
