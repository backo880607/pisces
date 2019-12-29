package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.Role;
import com.pisces.platform.user.service.RoleService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/Role")
public class RoleController extends EntityController<Role, RoleService> {

}
