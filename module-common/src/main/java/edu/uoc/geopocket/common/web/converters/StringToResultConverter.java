package edu.uoc.geopocket.common.web.converters;

import edu.uoc.geopocket.common.Result;
import org.springframework.core.convert.converter.Converter;

public class StringToResultConverter implements Converter<String, Result> {

    @Override
    public Result convert(String source) {
        int integer;
        try {
            integer = Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return null;
        }
        return Result.getResult(integer);
    }

}
