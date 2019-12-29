package com.pisces.platform.core.service;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.exception.SystemException;
import com.pisces.platform.core.utils.Primary;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private static Map<Class<? extends EntityObject>, EntityService<? extends EntityObject>> services = new HashMap<>();

    protected ServiceManager() {}

    public static void register(Class<? extends EntityObject> clazz, EntityService<? extends EntityObject> service) {
        if (services.containsKey(clazz)) {
            throw new SystemException(clazz.getName() + " has registered!");
        }
        services.put(clazz, service);
        Primary.get().registerEntityClass(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntityObject> EntityService<T> getService(Class<T> clazz) {
        EntityService<T> service = (EntityService<T>) services.get(clazz);
        if (service == null) {
            throw new SystemException(clazz.getName() + " has bind a service!");
        }
        return service;
    }

}
