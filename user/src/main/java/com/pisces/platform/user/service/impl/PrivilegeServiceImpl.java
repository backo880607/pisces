package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Privilege;
import com.pisces.platform.user.dao.PrivilegeDao;
import com.pisces.platform.user.service.PrivilegeService;
import org.springframework.stereotype.Service;

@Service
class PrivilegeServiceImpl extends EntityServiceImpl<Privilege, PrivilegeDao> implements PrivilegeService {

}
