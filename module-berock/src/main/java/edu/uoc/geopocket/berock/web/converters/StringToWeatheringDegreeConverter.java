package edu.uoc.geopocket.berock.web.converters;

import edu.uoc.geopocket.berock.common.WeatheringDegree;
import org.springframework.core.convert.converter.Converter;

public class StringToWeatheringDegreeConverter implements Converter<String, WeatheringDegree> {

    @Override
    public WeatheringDegree convert(String source) {
        int integer;
        try {
            integer = Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return null;
        }
        return WeatheringDegree.getWeatheringDegree(integer);
    }

}
