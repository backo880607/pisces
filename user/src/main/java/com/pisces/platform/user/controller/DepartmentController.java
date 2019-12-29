package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.Department;
import com.pisces.platform.user.service.DepartmentService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/Department")
public class DepartmentController extends EntityController<Department, DepartmentService> {

}
