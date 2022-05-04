package by.teachmeskills.spring.veterinary_clinic.validation;

import by.teachmeskills.spring.veterinary_clinic.validation.validator.CheckEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckEmailValidator.class)
public @interface CheckEmail {
    String pattern() default "(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    String message() default "Invalid email!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
