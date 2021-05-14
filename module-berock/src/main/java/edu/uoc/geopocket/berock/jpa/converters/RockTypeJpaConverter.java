package edu.uoc.geopocket.berock.jpa.converters;

import edu.uoc.geopocket.berock.common.RockType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Slf4j
@Converter
public class RockTypeJpaConverter implements AttributeConverter<RockType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RockType code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return code.getId();
    }

    @Override
    public RockType convertToEntityAttribute(Integer integer) {
        if (Objects.isNull(integer)) {
            return null;
        }
        return RockType.getRockType(integer);
    }
}
