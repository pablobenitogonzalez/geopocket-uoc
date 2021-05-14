package edu.uoc.geopocket.liquec.jpa.converters;

import edu.uoc.geopocket.liquec.common.LiquecCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Slf4j
@Converter
public class CodeJpaConverter implements AttributeConverter<LiquecCode, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LiquecCode code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return code.getId();
    }

    @Override
    public LiquecCode convertToEntityAttribute(Integer integer) {
        if (Objects.isNull(integer)) {
            return null;
        }
        return LiquecCode.getLiquecCode(integer);
    }
}
