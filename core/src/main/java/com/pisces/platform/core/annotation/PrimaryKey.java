package com.pisces.platform.core.annotation;

import com.pisces.platform.core.validator.InsertGroup;
import com.pisces.platform.core.validator.PrimaryKeyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PrimaryKeyValidator.class})
public @interface PrimaryKey {
    String[] fields() default {};

    String message() default "{WebMessage.CREATE}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
