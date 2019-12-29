package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.dao.FieldInfoDao;
import com.pisces.platform.integration.service.FieldInfoService;
import org.springframework.stereotype.Service;

@Service
class FieldInfoServiceImpl extends EntityServiceImpl<FieldInfo, FieldInfoDao> implements FieldInfoService {

}
