package com.pisces.platform.core.utils;

import com.pisces.platform.core.Initializer;
import com.pisces.platform.core.dao.DaoManager;
import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.exception.SystemException;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.service.ServiceManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppUtils {
    private static ApplicationContext context;
    private static Primary primary;
    private static Map<String, UserData> userDatas = new ConcurrentHashMap<>();
    private static ThreadLocal<UserData> curUserData = new ThreadLocal<>();

    protected AppUtils() {}

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        AppUtils.context = context;
        Initializer.execute();
    }

    public static String getUsername() {
        return curUserData.get() == null ? "system" : curUserData.get().username;
    }

    public static String getProperty(String name) {
        return context.getEnvironment().getProperty(name);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return context.getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    static Primary getPrimary() {
        return primary;
    }

    static void setPrimary(Primary obj) {
        primary = obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getUserData(Class<T> clazz) {
        UserData userData = curUserData.get();
        if (userData == null) {
            throw new SystemException("current thread not bind user data");
        }

        Object data = userData.data.get(clazz);
        if (data == null) {
            try {
                data = clazz.newInstance();
                userData.data.put(clazz, data);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return (T) data;
    }


    public static void login(String username) {
        UserData userData = userDatas.get(username);
        if (userData == null) {
            userData = new UserData();
            userData.username = username;
            userDatas.put(username, userData);
        }
        DaoManager.login(username);
        switchUser(username);
    }

    public static void logout(String username) {
        userDatas.remove(username);
        curUserData.set(null);
        DaoManager.logout(username);
    }

    public static boolean switchUser(String username) {
        UserData userData = userDatas.get(username);
        if (userData == null) {
            return false;
        }

        curUserData.set(userData);
        return DaoManager.switchUser(username);
    }

    public static PropertyService getPropertyService() {
        UserData userData = curUserData.get();
        if (userData == null) {
            throw new SystemException("current thread not bind user data");
        }

        if (userData.propertyService == null) {
            userData.propertyService = (PropertyService) ServiceManager.getService(Property.class);
        }
        return userData.propertyService;
    }

    public static class UserData {
        public String username = "";
        public Map<Class<?>, Object> data = new ConcurrentHashMap<>();
        public PropertyService propertyService;
    }
}
