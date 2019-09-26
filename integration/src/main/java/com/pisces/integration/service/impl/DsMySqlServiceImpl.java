package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.integration.bean.DsMySql;
import com.pisces.integration.dao.DsMySqlDao;
import com.pisces.integration.service.DsMySqlService;
import com.pisces.rds.provider.base.MySqlProvider;

@Service
class DsMySqlServiceImpl extends SqlDataSourceServiceImpl<DsMySql, DsMySqlDao> implements DsMySqlService {

	public DsMySqlServiceImpl() {
		super(new MySqlProvider());
	}
}
