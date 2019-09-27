package com.pisces.core.primary.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pisces.core.entity.EntityObject;

public final class FactoryManager {
	//private static List<Class<? extends EntityObject>> classes = new ArrayList<Class<? extends EntityObject>>();
	private static Map<Class<? extends EntityObject>, EntityFactory> factories = new HashMap<>();
	private static Map<String, EntityFactory> factoryNames = new HashMap<>();
	
	static {
		EntityFactory factory = new EntityFactory(EntityObject.class);
		FactoryManager.factories.put(EntityObject.class, factory);
		FactoryManager.factoryNames.put(EntityObject.class.getSimpleName(), factory);
	}
	
	public static void init() {
		for (Class<? extends EntityObject> clazz : factories.keySet()) {
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
	}
	
	@SuppressWarnings("unchecked")
	public static void registerEntityClass(Class<? extends EntityObject> clazz) {
		if (factories.containsKey(clazz)) {
			return;
		}
		EntityFactory factory = new EntityFactory(clazz);
		FactoryManager.factories.put(clazz, factory);
		FactoryManager.factoryNames.put(clazz.getSimpleName(), factory);
		registerEntityClass((Class<? extends EntityObject>)clazz.getSuperclass());
	}
	
	public static Set<Class<? extends EntityObject>> getEntityClasses() {
		return factories.keySet();
	}
	
	public static Class<? extends EntityObject> getEntityClass(String name) {
		return getFactory(name).getEntityClass();
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
			throw new UnsupportedOperationException(clazz.getName() + " has not registered!");
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
			throw new UnsupportedOperationException(name + " has not registered!");
		}
		return entityFactory;
	}
	
	
}
