package edu.uoc.geopocket.common.exceptions;

public class GeoPocketException extends RuntimeException {
    public GeoPocketException(String errorMessage) {
        super(errorMessage);
    }
    public GeoPocketException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
