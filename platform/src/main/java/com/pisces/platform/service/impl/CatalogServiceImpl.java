package com.pisces.platform.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.Catalog;
import com.pisces.platform.dao.CatalogDao;
import com.pisces.platform.service.CatalogService;

@Service
public class CatalogServiceImpl extends EntityServiceImpl<Catalog, CatalogDao> implements CatalogService {

}
