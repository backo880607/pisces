package com.pisces.core.service;

import java.util.HashMap;
import java.util.Map;

import com.pisces.core.entity.EntityObject;
import com.pisces.core.exception.SystemException;
import com.pisces.core.utils.Primary;

public class ServiceManager {
	private static Map<Class<? extends EntityObject>, EntityService<? extends EntityObject>> services = new HashMap<>();

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
