package com.pisces.platform.user.service;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.user.bean.Tenant;

public interface TenantService extends EntityService<Tenant> {
	void register(Tenant tenant);
	void unregister(Tenant tenant);
}
