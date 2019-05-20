package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsExcelCsv;
import com.pisces.integration.service.DsExcelCsvService;

@RestController
@RequestMapping("/integration/ExcelCsv")
public class DsExcelCsvController extends DataSourceController<DsExcelCsv, DsExcelCsvService> {

}
