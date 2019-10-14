package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsLocaleFile;
import com.pisces.integration.service.DsLocaleFileService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DsLocaleFile")
public class DsLocaleFileController extends EntityController<DsLocaleFile, DsLocaleFileService> {

}
