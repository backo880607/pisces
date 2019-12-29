package com.pisces.platform.integration.service.impl.sql;

import com.pisces.platform.integration.enums.SQL_TYPE;
import com.pisces.platform.rds.provider.base.SqlServerProvider;
import org.springframework.stereotype.Service;

public class DsSQLServerService extends SqlDataSourceService<SQL_TYPE> {

    public DsSQLServerService() {
        super(new SqlServerProvider());
    }

}