package com.pisces.platform.dao;

import org.springframework.stereotype.Component;

import com.pisces.nosql.dao.MongoDao;
import com.pisces.platform.bean.Catalog;

@Component
public class CatalogDao extends MongoDao<Catalog> {

}
