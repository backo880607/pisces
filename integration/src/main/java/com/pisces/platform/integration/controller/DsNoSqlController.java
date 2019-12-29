package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DsNoSql;
import com.pisces.platform.integration.service.DsNoSqlService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DsNoSql")
public class DsNoSqlController extends EntityController<DsNoSql, DsNoSqlService> {

}
