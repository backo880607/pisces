package com.pisces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.user.bean.Privilege;
import com.pisces.user.service.PrivilegeService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/user/Privilege")
public class PrivilegeController extends EntityController<Privilege, PrivilegeService> {

}
