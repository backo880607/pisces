package com.pisces.platform.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.Form;
import com.pisces.platform.dao.FormDao;
import com.pisces.platform.service.FormService;

@Service
public class FormServiceImpl extends EntityServiceImpl<Form, FormDao> implements FormService {

}
