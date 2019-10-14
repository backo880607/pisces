package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsSql;
import com.pisces.integration.dao.DsSqlDao;
import com.pisces.integration.service.DsSqlService;

@Service
public class DsSqlServiceImpl extends EntityServiceImpl<DsSql, DsSqlDao> implements DsSqlService {

}
