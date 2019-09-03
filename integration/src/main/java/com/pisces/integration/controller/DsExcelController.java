package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsExcel;
import com.pisces.integration.service.DsExcelService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/Excel")
public class DsExcelController extends EntityController<DsExcel, DsExcelService> {

}
