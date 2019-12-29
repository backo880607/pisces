package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.dao.DataSourceDao;
import com.pisces.platform.integration.service.DataSourceService;
import org.springframework.stereotype.Service;

@Service
class DataSourceServiceImpl extends EntityServiceImpl<DataSource, DataSourceDao> implements DataSourceService {
}
