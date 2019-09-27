package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsJsonFile;
import com.pisces.integration.service.DsJsonFileService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/JsonFile")
public class DsJsonFileController extends EntityController<DsJsonFile, DsJsonFileService> {

}
