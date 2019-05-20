package com.pisces.user.service;

import com.pisces.core.service.EntityService;
import com.pisces.user.bean.Tenant;

public interface TenantService extends EntityService<Tenant> {
	void register(Tenant tenant);
	void unregister(Tenant tenant);
}
