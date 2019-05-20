package com.pisces.user.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.user.bean.Privilege;
import com.pisces.user.dao.PrivilegeDao;
import com.pisces.user.service.PrivilegeService;

@Service
class PrivilegeServiceImpl extends EntityServiceImpl<Privilege, PrivilegeDao> implements PrivilegeService {

}
