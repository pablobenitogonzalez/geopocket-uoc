package edu.uoc.geopocket.berock.web.converters;

import edu.uoc.geopocket.berock.common.RockType;
import org.springframework.core.convert.converter.Converter;

public class StringToRockTypeConverter implements Converter<String, RockType> {

    @Override
    public RockType convert(String source) {
        int integer;
        try {
            integer = Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return null;
        }
        return RockType.getRockType(integer);
    }

}
