package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsHttp;
import com.pisces.integration.service.DsHttpService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DsHttp")
public class DsHttpController extends EntityController<DsHttp, DsHttpService> {

}
