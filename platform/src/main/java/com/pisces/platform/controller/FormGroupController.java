package com.pisces.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.FormGroup;
import com.pisces.platform.service.FormGroupService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/FormGroup")
public class FormGroupController extends EntityController<FormGroup, FormGroupService> {

}
