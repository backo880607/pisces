package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsOracle;
import com.pisces.integration.service.DsOracleService;

@RestController
@RequestMapping("/integration/Oracle")
public class DsOracleController extends SqlDataSourceController<DsOracle, DsOracleService> {

}
