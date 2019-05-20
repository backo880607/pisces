package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.service.FieldInfoService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/FieldInfo")
public class FieldInfoController extends EntityController<FieldInfo, FieldInfoService> {

}
