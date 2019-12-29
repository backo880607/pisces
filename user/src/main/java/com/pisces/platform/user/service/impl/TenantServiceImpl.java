package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Tenant;
import com.pisces.platform.user.dao.TenantDao;
import com.pisces.platform.user.service.TenantService;
import org.springframework.stereotype.Service;

@Service
class TenantServiceImpl extends EntityServiceImpl<Tenant, TenantDao> implements TenantService {

	@Override
	public void register(Tenant tenant) {
		
	}

	@Override
	public void unregister(Tenant tenant) {
		
	}

}
