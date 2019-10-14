package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsTcp;
import com.pisces.integration.dao.DsTcpDao;
import com.pisces.integration.service.DsTcpService;

@Service
public class DsTcpServiceImpl extends EntityServiceImpl<DsTcp, DsTcpDao> implements DsTcpService {

}
