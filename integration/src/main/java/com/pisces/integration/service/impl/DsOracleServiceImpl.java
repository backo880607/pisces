package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsOracle;
import com.pisces.integration.dao.DsOracleDao;
import com.pisces.integration.service.DsOracleService;
import com.pisces.rds.provider.base.OracleProvider;

@Service
class DsOracleServiceImpl extends SqlDataSourceServiceImpl<DsOracle, DsOracleDao> implements DsOracleService {
	
	public DsOracleServiceImpl() {
		super(new OracleProvider());
	}
}
