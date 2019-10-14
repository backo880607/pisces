package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsStream;
import com.pisces.integration.service.DsStreamService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DsStream")
public class DsStreamController extends EntityController<DsStream, DsStreamService> {

}
