package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.Privilege;
import com.pisces.platform.user.service.PrivilegeService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user/Privilege")
public class PrivilegeController extends EntityController<Privilege, PrivilegeService> {

}
