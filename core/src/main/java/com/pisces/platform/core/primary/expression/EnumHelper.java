package com.pisces.platform.core.primary.expression;

import com.pisces.platform.core.BasicProperties;
import com.pisces.platform.core.utils.AppUtils;
import com.pisces.platform.core.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class EnumHelper {
    private static Map<String, Class<?>> values = new HashMap<>();

    protected EnumHelper() {}

    public static void init() {
        Map<String, BasicProperties> configs = AppUtils.getBeansOfType(BasicProperties.class);
        for (Entry<String, BasicProperties> entry : configs.entrySet()) {
            BasicProperties property = entry.getValue();
            if (property.getEnumPath() != null) {
                for (String enumPath : property.getEnumPath().split(";")) {
                    if (enumPath.isEmpty()) {
                        continue;
                    }

                    register(enumPath);
                }
            }
        }
    }

    public static void register(String packPath) {
        for (Class<?> cls : FileUtils.loadClass(packPath)) {
            if (Enum.class.isAssignableFrom(cls)) {
                values.put(cls.getSimpleName(), cls);
            }
        }
    }

    public static Class<?> get(String name) {
        return values.get(name);
    }
}
