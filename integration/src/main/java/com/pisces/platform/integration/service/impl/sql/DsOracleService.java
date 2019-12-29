package com.pisces.platform.integration.service.impl.sql;

import com.pisces.platform.integration.enums.SQL_TYPE;
import com.pisces.platform.rds.provider.base.OracleProvider;
import org.springframework.stereotype.Service;

public class DsOracleService extends SqlDataSourceService<SQL_TYPE> {

    public DsOracleService() {
        super(new OracleProvider());
    }

}
