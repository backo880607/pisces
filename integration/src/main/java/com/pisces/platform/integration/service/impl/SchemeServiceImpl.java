package com.pisces.platform.integration.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.integration.dao.SchemeDao;
import com.pisces.platform.integration.service.SchemeService;
import org.springframework.stereotype.Service;

@Service
class SchemeServiceImpl extends EntityServiceImpl<Scheme, SchemeDao> implements SchemeService {

}
