package com.pisces.user.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.user.bean.Tenant;
import com.pisces.user.dao.TenantDao;
import com.pisces.user.service.TenantService;

@Service
class TenantServiceImpl extends EntityServiceImpl<Tenant, TenantDao> implements TenantService {

	@Override
	public void register(Tenant tenant) {
		
	}

	@Override
	public void unregister(Tenant tenant) {
		
	}

}
