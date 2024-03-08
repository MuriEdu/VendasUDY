package muri.ramos.vendas.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import muri.ramos.vendas.domain.validation.constraintsValidation.NotEmptyListValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME )
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "List can not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
