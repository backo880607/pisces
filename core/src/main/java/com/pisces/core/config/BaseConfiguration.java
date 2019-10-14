package com.pisces.core.config;

import java.util.Map;
import java.util.Map.Entry;

import com.pisces.core.utils.AppUtils;

public class BaseConfiguration {
	public static void invokeInit() {
		Map<String, BaseConfiguration> configurations =  AppUtils.getBeansOfType(BaseConfiguration.class);
		for (Entry<String, BaseConfiguration> entry : configurations.entrySet()) {
			entry.getValue().init();
		}
	}

	public void init() {}
}
