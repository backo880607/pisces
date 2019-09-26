package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsSqlServer;
import com.pisces.integration.dao.DsSQLServerDao;
import com.pisces.integration.service.DsSQLServerService;
import com.pisces.rds.provider.base.SqlServerProvider;

@Service
class DsSQLServerServiceImpl extends SqlDataSourceServiceImpl<DsSqlServer, DsSQLServerDao> implements DsSQLServerService {
	
	public DsSQLServerServiceImpl() {
		super(new SqlServerProvider());
	}

}