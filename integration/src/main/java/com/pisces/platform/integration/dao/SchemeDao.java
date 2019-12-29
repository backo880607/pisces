package com.pisces.platform.integration.dao;

import com.pisces.platform.integration.bean.Scheme;
import com.pisces.platform.rds.common.SQLMemoryDao;
import org.springframework.stereotype.Component;

@Component
public class SchemeDao extends SQLMemoryDao<Scheme> {
}
