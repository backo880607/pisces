package com.pisces.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ELFunction {
	boolean flexible() default false;
	int returnBy() default 0;	// 返回类型为指定的参数类型。
	int options() default 0; // 从指定参数开始是可选的。
}
