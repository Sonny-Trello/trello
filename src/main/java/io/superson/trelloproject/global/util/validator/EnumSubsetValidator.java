package io.superson.trelloproject.global.util.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumSubsetValidator implements ConstraintValidator<EnumSubsetOf, String> {

    private Class<? extends Enum<?>> enumValues;

    @Override
    public void initialize(EnumSubsetOf constraintAnnotation) {
        this.enumValues = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String valueUpperCase = value.toUpperCase();
        for (Enum<?> enumValue : enumValues.getEnumConstants()) {
            if (enumValue.name().equals(valueUpperCase)) {
                return true;
            }
        }

        return false;
    }

}
