package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.FieldInfo;
import com.pisces.integration.dao.FieldInfoDao;
import com.pisces.integration.service.FieldInfoService;

@Service
class FieldInfoServiceImpl extends EntityServiceImpl<FieldInfo, FieldInfoDao> implements FieldInfoService {

}
