package com.pisces.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pisces.core.enums.PROPERTY_TYPE;
import com.pisces.core.relation.RelationKind;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyMeta {
	String name() default "";
	// 仅供内部使用
	boolean internal() default false;
	boolean unique() default false;
	boolean modifiable() default true;
	boolean display() default true;
	PROPERTY_TYPE type() default PROPERTY_TYPE.NONE;
	RelationKind kind() default RelationKind.None;
}
