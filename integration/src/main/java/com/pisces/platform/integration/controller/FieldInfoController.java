package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.service.FieldInfoService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/FieldInfo")
public class FieldInfoController extends EntityController<FieldInfo, FieldInfoService> {

}
