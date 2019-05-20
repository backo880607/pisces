package com.pisces.user.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.user.bean.Role;
import com.pisces.user.dao.RoleDao;
import com.pisces.user.service.RoleService;

@Service
class RoleServiceImpl extends EntityServiceImpl<Role, RoleDao> implements RoleService {

}
