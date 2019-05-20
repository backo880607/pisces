package com.pisces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.user.bean.Department;
import com.pisces.user.service.DepartmentService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/user/Department")
public class DepartmentController extends EntityController<Department, DepartmentService> {

}
