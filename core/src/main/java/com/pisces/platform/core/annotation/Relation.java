package com.pisces.platform.core.annotation;

import com.pisces.platform.core.relation.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
    String clazz();

    String sign() default "";

    Type type();

    boolean owner() default false;
}
