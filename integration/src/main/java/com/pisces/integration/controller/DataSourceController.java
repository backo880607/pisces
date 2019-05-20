package com.pisces.integration.controller;

import com.pisces.integration.bean.DataSource;
import com.pisces.integration.service.DataSourceService;
import com.pisces.web.controller.EntityController;

public class DataSourceController<T extends DataSource, S extends DataSourceService<T>> extends EntityController<T, S> {

}
