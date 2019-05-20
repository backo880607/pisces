package com.pisces.platform.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.FormGroup;
import com.pisces.platform.dao.FormGroupDao;
import com.pisces.platform.service.FormGroupService;

@Service
public class FormGroupServiceImpl extends EntityServiceImpl<FormGroup, FormGroupDao> implements FormGroupService {

}
