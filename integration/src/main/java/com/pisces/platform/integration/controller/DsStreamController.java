package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DsStream;
import com.pisces.platform.integration.service.DsStreamService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DsStream")
public class DsStreamController extends EntityController<DsStream, DsStreamService> {

}
