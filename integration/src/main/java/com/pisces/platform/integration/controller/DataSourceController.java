package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.service.DataSourceService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/DataSource")
public class DataSourceController extends EntityController<DataSource, DataSourceService> {

}
