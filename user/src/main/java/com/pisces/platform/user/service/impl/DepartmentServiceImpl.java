package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.Department;
import com.pisces.platform.user.dao.DepartmentDao;
import com.pisces.platform.user.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
class DepartmentServiceImpl extends EntityServiceImpl<Department, DepartmentDao> implements DepartmentService {

}
