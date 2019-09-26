package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsSqlite;
import com.pisces.integration.dao.DsSQLiteDao;
import com.pisces.integration.service.DsSQLiteService;
import com.pisces.rds.provider.base.SQLiteProvider;

@Service
class DsSQLiteServiceImpl extends SqlDataSourceServiceImpl<DsSqlite, DsSQLiteDao> implements DsSQLiteService {
	public DsSQLiteServiceImpl() {
		super(new SQLiteProvider());
	}
}
