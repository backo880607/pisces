package com.pisces.platform.integration.service.impl.sql;

import com.pisces.platform.integration.enums.SQL_TYPE;
import com.pisces.platform.rds.provider.base.MySqlProvider;
import org.springframework.stereotype.Service;

public class DsMySqlService extends SqlDataSourceService<SQL_TYPE> {

    public DsMySqlService() {
        super(new MySqlProvider());
    }

}
