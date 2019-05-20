package com.pisces.integration.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.integration.bean.Scheme;
import com.pisces.integration.dao.SchemeDao;
import com.pisces.integration.service.SchemeService;

@Service
class SchemeServiceImpl extends EntityServiceImpl<Scheme, SchemeDao> implements SchemeService {

}
