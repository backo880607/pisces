package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DsSql;
import com.pisces.platform.integration.dao.DsSqlDao;
import com.pisces.platform.integration.service.DsSqlService;
import org.springframework.stereotype.Service;

@Service
public class DsSqlServiceImpl extends EntityServiceImpl<DsSql, DsSqlDao> implements DsSqlService {

}
