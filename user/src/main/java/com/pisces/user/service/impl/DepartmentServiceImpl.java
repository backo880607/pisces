package com.pisces.user.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.user.bean.Department;
import com.pisces.user.dao.DepartmentDao;
import com.pisces.user.service.DepartmentService;

@Service
class DepartmentServiceImpl extends EntityServiceImpl<Department, DepartmentDao> implements DepartmentService {

}
