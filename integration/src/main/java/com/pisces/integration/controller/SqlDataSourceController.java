package com.pisces.integration.controller;

import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.service.SqlDataSourceService;
import com.pisces.web.controller.EntityController;

public class SqlDataSourceController<T extends SqlDataSource, S extends SqlDataSourceService<T>> extends EntityController<T, S> {

}
