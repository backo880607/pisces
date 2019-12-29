package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Role;
import com.pisces.platform.user.dao.RoleDao;
import com.pisces.platform.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
class RoleServiceImpl extends EntityServiceImpl<Role, RoleDao> implements RoleService {

}
