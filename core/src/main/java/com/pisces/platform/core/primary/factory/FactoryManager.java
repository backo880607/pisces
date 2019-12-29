package com.pisces.platform.core.primary.factory;

import com.pisces.platform.core.entity.EntityObject;

import java.util.*;

public final class FactoryManager {
    private static Map<Class<? extends EntityObject>, EntityFactory> factories = new HashMap<>();
    private static Map<String, EntityFactory> factoryNames = new HashMap<>();

    static {
        EntityFactory factory = new EntityFactory(EntityObject.class);
        FactoryManager.factories.put(EntityObject.class, factory);
        FactoryManager.factoryNames.put(EntityObject.class.getSimpleName(), factory);
    }

    protected FactoryManager() {}

    public static void init() {
        for (Map.Entry<Class<? extends EntityObject>, EntityFactory> entry : factories.entrySet()) {
            Class<? extends EntityObject> clazz = entry.getKey();
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
        registerEntityClass((Class<? extends EntityObject>) clazz.getSuperclass());
    }

    public static Set<Class<? extends EntityObject>> getEntityClasses() {
        return factories.keySet();
    }

    public static Class<? extends EntityObject> getEntityClass(String name) {
        return getFactory(name).getEntityClass();
    }

    public static List<EntityFactory> getFactories() {
        return new ArrayList<>(FactoryManager.factories.values());
    }

    public static EntityFactory getFactory(Class<? extends EntityObject> clazz) {
        EntityFactory factory = FactoryManager.factories.get(clazz);
        if (factory == null) {
            throw new UnsupportedOperationException(clazz.getName() + " has not registered!");
        }
        return factory;
    }

    public static boolean hasFactory(Class<? extends EntityObject> clazz) {
        return FactoryManager.factories.containsKey(clazz);
    }

    public static EntityFactory getFactory(String name) {
        EntityFactory entityFactory = FactoryManager.factoryNames.get(name);
        if (entityFactory == null) {
            throw new UnsupportedOperationException(name + " has not registered!");
        }
        return entityFactory;
    }


}
