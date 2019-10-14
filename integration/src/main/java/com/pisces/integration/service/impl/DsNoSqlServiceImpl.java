package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsNoSql;
import com.pisces.integration.dao.DsNoSqlDao;
import com.pisces.integration.service.DsNoSqlService;

@Service
public class DsNoSqlServiceImpl extends EntityServiceImpl<DsNoSql, DsNoSqlDao> implements DsNoSqlService {

}
