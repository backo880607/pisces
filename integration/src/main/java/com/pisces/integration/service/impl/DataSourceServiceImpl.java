package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DataSource;
import com.pisces.integration.dao.DataSourceDao;
import com.pisces.integration.service.DataSourceService;

@Service
class DataSourceServiceImpl extends EntityServiceImpl<DataSource, DataSourceDao> implements DataSourceService {
}
