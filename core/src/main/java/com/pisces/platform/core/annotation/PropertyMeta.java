package com.pisces.platform.core.annotation;

import com.pisces.platform.core.enums.PROPERTY_TYPE;
import com.pisces.platform.core.relation.RelationKind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyMeta {
    String name() default "";

    boolean unique() default false;

    boolean modifiable() default true;

    short preci() default 2;

    // 控制对外界是否可见，不可见的不会创建Property
    // 若也不想进行持久化，请用transient修饰符。
    boolean visible() default true;

    // 默认是否在界面显示
    boolean display() default true;

    PROPERTY_TYPE type() default PROPERTY_TYPE.NONE;

    RelationKind kind() default RelationKind.None;

    // 是否为长度超大的数据类型
    boolean large() default false;
}
