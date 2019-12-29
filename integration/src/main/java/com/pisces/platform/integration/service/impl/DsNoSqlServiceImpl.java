package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DsNoSql;
import com.pisces.platform.integration.dao.DsNoSqlDao;
import com.pisces.platform.integration.service.DsNoSqlService;
import org.springframework.stereotype.Service;

@Service
public class DsNoSqlServiceImpl extends EntityServiceImpl<DsNoSql, DsNoSqlDao> implements DsNoSqlService {

}
