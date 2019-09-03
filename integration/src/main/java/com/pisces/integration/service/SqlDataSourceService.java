package com.pisces.integration.service;

import com.pisces.core.service.EntityService;
import com.pisces.integration.bean.SqlDataSource;

public interface SqlDataSourceService<T extends SqlDataSource> extends EntityService<T>, DataSourceAdapter {

}
