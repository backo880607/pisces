package com.pisces.integration.controller;

import com.pisces.integration.bean.SqlDataSource;
import com.pisces.integration.service.SqlDataSourceService;

public class SqlDataSourceController<T extends SqlDataSource, S extends SqlDataSourceService<T>> extends DataSourceController<T, S> {

}
