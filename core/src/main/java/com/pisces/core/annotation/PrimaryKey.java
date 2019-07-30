package com.pisces.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.pisces.core.validator.InsertGroup;
import com.pisces.core.validator.PrimaryKeyValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={PrimaryKeyValidator.class})
public @interface PrimaryKey {
	String[] fields() default {};
	String message() default "{WebMessage.CREATE}";
    Class<?>[] groups() default { InsertGroup.class };
    Class<? extends Payload>[] payload() default {};
}
