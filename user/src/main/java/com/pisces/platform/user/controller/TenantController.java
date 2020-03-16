package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.Account;
import com.pisces.platform.user.bean.Tenant;
import com.pisces.platform.user.service.TenantService;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user/Tenant")
public class TenantController extends EntityController<Tenant, TenantService> {
	@RequestMapping("register")
	public ResponseData register(Account account) {
		return succeed();
	}
	
	@RequestMapping("unregister")
	public ResponseData unregister(Account account) {
		return succeed();
	}
}
