package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DsHttp;
import com.pisces.platform.integration.service.DsHttpService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DsHttp")
public class DsHttpController extends EntityController<DsHttp, DsHttpService> {

}
