package edu.uoc.geopocket.common.jpa.converters;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Converter
public class BigDecimalConverter implements AttributeConverter<BigDecimal, Double> {

    @Override
    public Double convertToDatabaseColumn(BigDecimal value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return value.doubleValue();
    }

    @Override
    public BigDecimal convertToEntityAttribute(Double value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return BigDecimal.valueOf(value);
    }
}
