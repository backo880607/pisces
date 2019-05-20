package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DsSqlite;
import com.pisces.integration.service.DsSQLiteService;

@RestController
@RequestMapping("/integration/SQLite")
public class DsSQLiteController extends SqlDataSourceController<DsSqlite, DsSQLiteService> {

}
