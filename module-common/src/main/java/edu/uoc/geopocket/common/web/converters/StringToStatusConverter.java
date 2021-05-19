package edu.uoc.geopocket.common.web.converters;

import edu.uoc.geopocket.common.Status;
import org.springframework.core.convert.converter.Converter;

public class StringToStatusConverter implements Converter<String, Status> {

    @Override
    public Status convert(String source) {
        int integer;
        try {
            integer = Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return null;
        }
        return Status.getStatus(integer);
    }

}
