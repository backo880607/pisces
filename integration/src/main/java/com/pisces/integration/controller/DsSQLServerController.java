package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsSqlServer;
import com.pisces.integration.service.DsSQLServerService;

@RestController
@RequestMapping("/integration/SQLServer")
public class DsSQLServerController extends SqlDataSourceController<DsSqlServer, DsSQLServerService> {

}
