package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.Account;
import com.pisces.platform.user.service.AccountService;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/Account")
public class AccountController extends EntityController<Account, AccountService> {
	@RequestMapping("hello")
	public ResponseData hello(Long id, Long user, BindingResult binder) {
		return succeed(id);
	}
	
	@RequestMapping("register")
	public ResponseData register(Account account) {
		getService().register(account);
		return succeed();
	}
	
	@RequestMapping("unregister")
	public ResponseData unregister(Account account) {
		getService().unregister(account);
		return succeed();
	}
	
	@PostMapping("checkPassword")
	public ResponseData checkPassword() {
		Map<String, String> value = new HashMap<>();
		value.put("name", "niuhaitao");
		return succeed(value);
	}
	
	@PostMapping("modifyPassword")
	public ResponseData modifyPassword() {
		return succeed();
	}
}
