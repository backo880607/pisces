package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsExcelCsv;
import com.pisces.integration.service.DsExcelCsvService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/ExcelCsv")
public class DsExcelCsvController extends EntityController<DsExcelCsv, DsExcelCsvService> {

}
