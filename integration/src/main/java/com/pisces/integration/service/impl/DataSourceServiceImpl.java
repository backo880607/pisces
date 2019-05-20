package com.pisces.integration.service.impl;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.service.DataSourceService;

abstract class DataSourceServiceImpl<T extends DataSource, D extends BaseDao<T>> extends EntityServiceImpl<T, D> implements DataSourceService<T> {
}
