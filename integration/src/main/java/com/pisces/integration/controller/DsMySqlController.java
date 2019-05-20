package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsMySql;
import com.pisces.integration.service.DsMySqlService;

@RestController
@RequestMapping("/integration/MySql")
public class DsMySqlController extends SqlDataSourceController<DsMySql, DsMySqlService> {

}
