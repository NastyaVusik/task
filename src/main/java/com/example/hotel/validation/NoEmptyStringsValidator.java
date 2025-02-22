package com.example.hotel.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NoEmptyStringsValidator implements ConstraintValidator<NoEmptyStrings, List<String>> {

    @Override
    public void initialize(NoEmptyStrings constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.stream().allMatch(str -> str != null && !str.trim().isEmpty());
    }
}
