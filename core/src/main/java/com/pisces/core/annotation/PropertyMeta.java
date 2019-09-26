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
	boolean unique() default false;
	boolean modifiable() default true;
	// 控制对外界是否可见，不可见的不会创建Property
	// 若也不想进行持久化，请用transient修饰符。
	boolean visiable() default true;
	PROPERTY_TYPE type() default PROPERTY_TYPE.NONE;
	RelationKind kind() default RelationKind.None;
	// 是否为长度超大的数据类型
	boolean large() default false;
}
