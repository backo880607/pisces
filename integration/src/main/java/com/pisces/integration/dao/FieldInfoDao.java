package com.pisces.integration.dao;

import org.springframework.stereotype.Component;

import com.pisces.integration.bean.FieldInfo;
import com.pisces.rds.common.SQLMemoryDao;

@Component
public class FieldInfoDao extends SQLMemoryDao<FieldInfo> {
}
