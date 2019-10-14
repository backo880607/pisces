package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsHttp;
import com.pisces.integration.dao.DsHttpDao;
import com.pisces.integration.service.DsHttpService;

@Service
public class DsHttpServiceImpl extends EntityServiceImpl<DsHttp, DsHttpDao> implements DsHttpService {

}
