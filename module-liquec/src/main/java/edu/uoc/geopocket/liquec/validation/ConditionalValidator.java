package edu.uoc.geopocket.liquec.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

    private String selected;
    private String required;
    private String message;
    private String value;
    private String decimalMin;
    private String decimalMax;

    @Override
    public void initialize(Conditional requiredIfChecked) {
        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        value = requiredIfChecked.value();
        decimalMin = requiredIfChecked.decimalMin();
        decimalMax = requiredIfChecked.decimalMax();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            final Object checkedValue = BeanUtils.getProperty(object, selected);
            if (value.equals(checkedValue)) {
                final String requiredValue = BeanUtils.getProperty(object, required);
                valid = requiredValue != null
                        && !isEmpty(requiredValue)
                        && Double.parseDouble(requiredValue) >= Double.parseDouble(decimalMin)
                        && Double.parseDouble(requiredValue) <= Double.parseDouble(decimalMax);
                log.debug("value: " + "" + requiredValue);
                if (!valid) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message).addPropertyNode(required).addConstraintViolation();
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Accessor method is not available for class : {}, exception : {}", object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            log.error("Field or method is not present on class : {}, exception : {}", object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            log.error("An exception occurred while accessing class : {}, exception : {}", object.getClass().getName(), e);
            e.printStackTrace();
            return false;
        }
        return valid;
    }
}
