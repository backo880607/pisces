package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.service.SchemeService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/Scheme")
public class SchemeController extends EntityController<Scheme, SchemeService> {

}
