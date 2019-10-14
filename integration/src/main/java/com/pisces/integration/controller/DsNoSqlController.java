package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsNoSql;
import com.pisces.integration.service.DsNoSqlService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DsNoSql")
public class DsNoSqlController extends EntityController<DsNoSql, DsNoSqlService> {

}
