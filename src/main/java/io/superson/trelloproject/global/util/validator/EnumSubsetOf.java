package io.superson.trelloproject.global.util.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumSubsetValidator.class)
public @interface EnumSubsetOf {

    Class<? extends Enum<?>> enumClass();

    String message() default "must be a valid enum value";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
