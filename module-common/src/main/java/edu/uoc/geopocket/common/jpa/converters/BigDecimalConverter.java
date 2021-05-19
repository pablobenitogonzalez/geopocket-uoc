package edu.uoc.geopocket.common.jpa.converters;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Slf4j
@Converter
public class BigDecimalConverter implements AttributeConverter<BigDecimal, Double> {

    @Override
    public Double convertToDatabaseColumn(BigDecimal bigDecimalValue) {
        if (bigDecimalValue == null) {
            return null;
        }

        return bigDecimalValue.doubleValue();
    }

    @Override
    public BigDecimal convertToEntityAttribute(Double doubleValue) {
        if (doubleValue == null) {
            return null;
        }

        return BigDecimal.valueOf(doubleValue);
    }

}
