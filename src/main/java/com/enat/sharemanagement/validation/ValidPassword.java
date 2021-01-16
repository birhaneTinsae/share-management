package com.enat.sharemanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must have 1 uppercase,1 lowercase, 1 digit and shouldn't have more than three consecutive digit or character ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
