package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.DataSource;
import com.pisces.integration.service.DataSourceService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/integration/DataSource")
public class DataSourceController extends EntityController<DataSource, DataSourceService> {

}
