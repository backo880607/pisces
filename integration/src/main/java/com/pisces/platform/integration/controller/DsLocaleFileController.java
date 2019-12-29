package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DsLocaleFile;
import com.pisces.platform.integration.service.DsLocaleFileService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DsLocaleFile")
public class DsLocaleFileController extends EntityController<DsLocaleFile, DsLocaleFileService> {

}
