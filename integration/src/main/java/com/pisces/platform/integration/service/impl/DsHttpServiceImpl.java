package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DsHttp;
import com.pisces.platform.integration.dao.DsHttpDao;
import com.pisces.platform.integration.service.DsHttpService;
import org.springframework.stereotype.Service;

@Service
public class DsHttpServiceImpl extends EntityServiceImpl<DsHttp, DsHttpDao> implements DsHttpService {

}
