package com.pisces.integration.service;

import com.pisces.core.service.EntityService;
import com.pisces.integration.bean.DataSource;

public interface XMLDataSourceService<T extends DataSource> extends EntityService<T>, DataSourceAdapter {

}
