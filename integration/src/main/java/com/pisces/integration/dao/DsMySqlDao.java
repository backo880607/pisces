package com.pisces.integration.dao;

import org.springframework.stereotype.Component;

import com.pisces.integration.bean.DsMySql;
import com.pisces.rds.common.SQLMemoryDao;

@Component
public class DsMySqlDao extends SQLMemoryDao<DsMySql> {
}
