package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.DsTcp;
import com.pisces.platform.integration.dao.DsTcpDao;
import com.pisces.platform.integration.service.DsTcpService;
import org.springframework.stereotype.Service;

@Service
public class DsTcpServiceImpl extends EntityServiceImpl<DsTcp, DsTcpDao> implements DsTcpService {

}
