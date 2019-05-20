package com.pisces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.user.bean.Account;
import com.pisces.user.bean.Tenant;
import com.pisces.user.service.TenantService;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

@RestController
@RequestMapping("/user/Tenant")
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
