package by.teachmeskills.spring.veterinary_clinic.validation.validator;

import by.teachmeskills.spring.veterinary_clinic.validation.CheckEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {
    private String pattern;

    @Override
    public void initialize(CheckEmail constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String emailToCheck, ConstraintValidatorContext constraintValidatorContext) {
        return emailToCheck.matches(pattern);
    }
}