package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.DsStream;
import com.pisces.integration.dao.DsStreamDao;
import com.pisces.integration.service.DsStreamService;

@Service
public class DsStreamServiceImpl extends EntityServiceImpl<DsStream, DsStreamDao> implements DsStreamService {

}
