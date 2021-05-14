package edu.uoc.geopocket.common.jpa.converters;

import edu.uoc.geopocket.common.Result;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Slf4j
@Converter
public class ResultJpaConverter implements AttributeConverter<Result, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Result result) {
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getId();
    }

    @Override
    public Result convertToEntityAttribute(Integer integer) {
        if (Objects.isNull(integer)) {
            return null;
        }
        return Result.getResult(integer);
    }
}
