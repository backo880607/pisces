package com.pisces.core.primary.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.RegisteredException;
import com.pisces.core.utils.EntityUtils;

public final class FactoryManager {
	private static Map<Class<? extends EntityObject>, EntityFactory> factories = new HashMap<>();
	private static Map<String, EntityFactory> factoryNames = new HashMap<>();
	
	public static void init() {
		EntityUtils.init();
		List<Class<? extends EntityObject>> clazzs = EntityUtils.getEntityClasses();
		for (Class<? extends EntityObject> clazz : clazzs) {
			EntityFactory factory = new EntityFactory(clazz);
			FactoryManager.factories.put(clazz, factory);
			FactoryManager.factoryNames.put(clazz.getSimpleName(), factory);
		}
		
		for (Class<? extends EntityObject> clazz : clazzs) {
			@SuppressWarnings("unchecked")
			Class<? extends EntityObject> superClazz = (Class<? extends EntityObject>) clazz.getSuperclass();
			EntityFactory factory = FactoryManager.factories.get(clazz);
			EntityFactory superFactory = FactoryManager.factories.get(superClazz);
			if (factory != null && superFactory != null) {
				factory.superFactory = superFactory;
				superFactory.childFactories.add(factory);
			}
		}
		
		FactoryHelp.initRelation();
		
		try {
			EntityUtils.checkProperty();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取所有工厂类
	 * @return
	 */
	public static List<EntityFactory> getFactories() {
		return new ArrayList<>(FactoryManager.factories.values());
	}
	
	/**
	 * 由类的Class获取对应的工厂类
	 * @param clazz
	 * @return
	 */
	public static EntityFactory getFactory(Class<? extends EntityObject> clazz) {
		EntityFactory factory = FactoryManager.factories.get(clazz);
		if (factory == null) {
			throw new RegisteredException(clazz.getName() + " has not registered!");
		}
		return factory;
	}
	
	/**
	 * 判断类的class是否存在对于的工厂类
	 * @param clazz
	 * @return
	 */
	public static boolean hasFactory(Class<? extends EntityObject> clazz) {
		return FactoryManager.factories.containsKey(clazz);
	}
	
	/**
	 * 由类名获取对应的工厂类
	 * @param name
	 * @return
	 */
	public static EntityFactory getFactory(String name) {
		EntityFactory entityFactory = FactoryManager.factoryNames.get(name);
		if (entityFactory == null) {
			throw new RegisteredException(name + " has not registered!");
		}
		return entityFactory;
	}
}
