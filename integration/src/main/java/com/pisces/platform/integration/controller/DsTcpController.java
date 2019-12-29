package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DsTcp;
import com.pisces.platform.integration.service.DsTcpService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DsTcp")
public class DsTcpController extends EntityController<DsTcp, DsTcpService> {

}
