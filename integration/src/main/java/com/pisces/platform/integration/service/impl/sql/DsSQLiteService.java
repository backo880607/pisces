package com.pisces.platform.integration.service.impl.sql;

import com.pisces.platform.integration.enums.SQL_TYPE;
import com.pisces.platform.rds.provider.base.SQLiteProvider;
import org.springframework.stereotype.Service;

public class DsSQLiteService extends SqlDataSourceService<SQL_TYPE> {

    public DsSQLiteService() {
        super(new SQLiteProvider());
    }

}
