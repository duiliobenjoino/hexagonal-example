package com.bd.example.aplication.adapters.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAndNotZeroValidator.class)
public @interface NotNullAndNotZero {
    String message() default "Value must not be null or zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

