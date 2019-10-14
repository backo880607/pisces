package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsTcp;
import com.pisces.integration.service.DsTcpService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DsTcp")
public class DsTcpController extends EntityController<DsTcp, DsTcpService> {

}
