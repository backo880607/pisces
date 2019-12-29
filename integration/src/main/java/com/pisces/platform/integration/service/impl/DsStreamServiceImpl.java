package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DsStream;
import com.pisces.platform.integration.dao.DsStreamDao;
import com.pisces.platform.integration.service.DsStreamService;
import org.springframework.stereotype.Service;

@Service
public class DsStreamServiceImpl extends EntityServiceImpl<DsStream, DsStreamDao> implements DsStreamService {

}
