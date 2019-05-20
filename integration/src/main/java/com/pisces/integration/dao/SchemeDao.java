package com.pisces.integration.dao;

import org.springframework.stereotype.Component;

import com.pisces.integration.bean.Scheme;
import com.pisces.rds.common.SQLMemoryDao;

@Component
public class SchemeDao extends SQLMemoryDao<Scheme> {
}
