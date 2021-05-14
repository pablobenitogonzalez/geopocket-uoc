package edu.uoc.geopocket.berock.jpa.converters;

import edu.uoc.geopocket.berock.common.WeatheringDegree;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Slf4j
@Converter
public class WeatheringDegreeTypeJpaConverter implements AttributeConverter<WeatheringDegree, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WeatheringDegree code) {
        if (Objects.isNull(code)) {
            return null;
        }
        return code.getId();
    }

    @Override
    public WeatheringDegree convertToEntityAttribute(Integer integer) {
        if (Objects.isNull(integer)) {
            return null;
        }
        return WeatheringDegree.getWeatheringDegree(integer);
    }
}
