package com.bd.example.aplication.adapters.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullAndNotZeroValidator implements ConstraintValidator<NotNullAndNotZero, Number> {

    @Override
    public boolean isValid(final Number value, final ConstraintValidatorContext context) {
        return value != null && value.doubleValue() != 0.0;
    }
}
