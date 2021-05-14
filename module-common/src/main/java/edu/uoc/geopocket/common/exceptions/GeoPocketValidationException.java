package edu.uoc.geopocket.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GeoPocketValidationException extends ConstraintViolationException {
    public GeoPocketValidationException(String errorMessage, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(errorMessage, constraintViolations);
    }
}
