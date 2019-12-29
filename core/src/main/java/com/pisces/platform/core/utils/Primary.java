package com.pisces.platform.core.utils;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.RelationKind;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

import java.util.List;
import java.util.Set;

public interface Primary {
    static Primary get() {
        if (AppUtils.getPrimary() == null) {
            synchronized (Primary.class) {
                if (AppUtils.getPrimary() == null) {
                    try {
                        Class<?> appServiceCls = Class.forName("com.pisces.platform.core.primary.PrimaryImpl");
                        AppUtils.setPrimary((Primary) appServiceCls.newInstance());
                    } catch (ClassNotFoundException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return AppUtils.getPrimary();
    }

    void init();

    void createRelation(EntityObject entity);

    IExpression createExpression(String str);

    void registerEntityClass(Class<? extends EntityObject> clazz);

    Set<Class<? extends EntityObject>> getEntityClasses();

    Class<? extends EntityObject> getEntityClass(String name);

    Class<? extends EntityObject> getSuperClass(Class<? extends EntityObject> clazz);

    Class<? extends EntityObject> getSuperClass(String name);

    List<Class<? extends EntityObject>> getChildClasses(Class<? extends EntityObject> clazz);

    Sign getRelationSign(Class<? extends EntityObject> clazz, String signName);

    Type getRelationType(Class<? extends EntityObject> clazz, Sign sign);

    RelationKind getRelationKind(Class<? extends EntityObject> clazz, Sign sign);

    Sign getRelationReverse(Class<? extends EntityObject> clazz, Sign sign);

    List<Sign> getRelationOwners(Class<? extends EntityObject> clazz);

    List<Sign> getRelationNotOwners(Class<? extends EntityObject> clazz);

    Class<? extends EntityObject> getRelationClass(Class<? extends EntityObject> clazz, Sign sign);

    Class<? extends EntityObject> getRelationClass(Class<? extends EntityObject> clazz, String signName);
}
