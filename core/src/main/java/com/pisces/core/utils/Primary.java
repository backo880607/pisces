package com.pisces.core.utils;

import java.util.List;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;
import com.pisces.core.relation.Type;

public interface Primary {
	public static Primary get() {
		if (AppUtils.getPrimary() == null) {
			synchronized (Primary.class) {
				if (AppUtils.getPrimary() == null) {
					try {
						Class<?> appServiceCls = Class.forName("com.pisces.core.primary.PrimaryImpl");
						AppUtils.setPrimary((Primary)appServiceCls.newInstance());
					} catch (ClassNotFoundException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
					}
				}
			}
		}
		return AppUtils.getPrimary();
	}
	
	void init();
	
	void createRelation(EntityObject entity);
	IExpression createExpression(String str);
	
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
