package edu.uoc.geopocket.liquec.web.converters;

import edu.uoc.geopocket.liquec.common.LiquecCode;
import org.springframework.core.convert.converter.Converter;

public class StringToCodeConverter implements Converter<String, LiquecCode> {

    @Override
    public LiquecCode convert(String source) {
        int integer;
        try {
            integer = Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return null;
        }
        return LiquecCode.getLiquecCode(integer);
    }

}
