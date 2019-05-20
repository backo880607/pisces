package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.Scheme;
import com.pisces.integration.service.SchemeService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/Scheme")
public class SchemeController extends EntityController<Scheme, SchemeService> {

}
