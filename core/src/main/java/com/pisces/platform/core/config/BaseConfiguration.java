package com.pisces.platform.core.config;

import com.pisces.platform.core.utils.AppUtils;

import java.util.Map;
import java.util.Map.Entry;

public class BaseConfiguration {
    public static void invokeInit() {
        Map<String, BaseConfiguration> configurations = AppUtils.getBeansOfType(BaseConfiguration.class);
        for (Entry<String, BaseConfiguration> entry : configurations.entrySet()) {
            entry.getValue().init();
        }
    }

    public void init() {
    }
}
