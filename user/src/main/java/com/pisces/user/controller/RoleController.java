package com.pisces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.user.bean.Role;
import com.pisces.user.service.RoleService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/user/Role")
public class RoleController extends EntityController<Role, RoleService> {

}
