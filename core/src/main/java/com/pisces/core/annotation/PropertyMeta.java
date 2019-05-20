package com.pisces.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pisces.core.enums.EditType;
import com.pisces.core.relation.RelationKind;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyMeta {
	String name() default "";
	// 仅供内部使用
	boolean internal() default false;
	boolean unique() default false;
	boolean modifiable() default true;
	boolean nullable() default false;
	boolean display() default true;
	EditType editType() default EditType.NONE;
	RelationKind kind() default RelationKind.None;
}
