package com.pisces.integration.dao;

import org.springframework.stereotype.Component;

import com.pisces.integration.bean.SchemeGroup;
import com.pisces.rds.common.SQLMemoryDao;

@Component
public class SchemeGroupDao extends SQLMemoryDao<SchemeGroup> {
}
