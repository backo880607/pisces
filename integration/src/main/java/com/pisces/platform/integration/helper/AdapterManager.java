package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.core.service.ServiceManager;
import com.pisces.platform.integration.bean.*;
import com.pisces.platform.integration.enums.*;
import com.pisces.platform.integration.service.impl.localefile.*;
import com.pisces.platform.integration.service.impl.sql.DsMySqlService;
import com.pisces.platform.integration.service.impl.sql.DsOracleService;
import com.pisces.platform.integration.service.impl.sql.DsSQLServerService;
import com.pisces.platform.integration.service.impl.sql.DsSQLiteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterManager {

    private static DataSourceAdapter getLocaleFileAdapter(DsLocaleFile localeFile) {
        if (localeFile.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (localeFile.getType()) {
            case PISCES:
                adapter = new DsPiscesFileService();
                break;
            case CSV:
                adapter = new DsExcelCsvService();
                break;
            case XLS:
                adapter = new DsExcelXlsService();
                break;
            case XLSX:
                adapter = new DsExcelXlsxService();
                break;
            case JSON:
                adapter = new DsJsonFileService();
                break;
            case XML:
                adapter = new DsXMLFileService();
                break;
        }

        return adapter;
    }

    private static DataSourceAdapter getSqlAdapter(DsSql sql) {
        if (sql.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (sql.getType()) {
            case MYSQL:
                adapter = new DsMySqlService();
                break;
            case SQLSERVER:
                adapter = new DsSQLServerService();
                break;
            case ORACLE:
                adapter = new DsOracleService();
                break;
            case SQLITE:
                adapter = new DsSQLiteService();
                break;
        }

        return adapter;
    }

    private static DataSourceAdapter getNoSqlAdapter(DsNoSql noSql) {
        if (noSql.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (noSql.getType()) {
            case MONGODB:
                break;
            case REDIS:
                break;
        }

        return adapter;
    }

    private static DataSourceAdapter getStreamAdapter(DsStream stream) {
        if (stream.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (stream.getType()) {
            case TABLE:
                break;
            case JSON:
                break;
            case XML:
                break;
        }

        return adapter;
    }

    private static DataSourceAdapter getHttpAdapter(DsHttp http) {
        if (http.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (http.getType()) {
            case TABLE:
                break;
            case JSON:
                break;
            case XML:
                break;
        }

        return adapter;
    }

    private static DataSourceAdapter getTcpAdapter(DsTcp tcp) {
        if (tcp.getType() == null) {
            return null;
        }

        DataSourceAdapter adapter = null;
        switch (tcp.getType()) {

            case TABLE:
                break;
            case JSON:
                break;
            case XML:
                break;
        }

        return adapter;
    }

    public static DataSourceAdapter getAdapter(DataSource source) {
        DataSourceAdapter adapter = null;
        if (source instanceof DsLocaleFile) {
            adapter = getLocaleFileAdapter((DsLocaleFile) source);
        } else if (source instanceof DsSql) {
            adapter = getSqlAdapter((DsSql) source);
        } else if (source instanceof DsNoSql) {
            adapter = getNoSqlAdapter((DsNoSql) source);
        } else if (source instanceof DsStream) {
            adapter = getStreamAdapter((DsStream) source);
        } else if (source instanceof DsHttp) {
            adapter = getHttpAdapter((DsHttp) source);
        } else if (source instanceof DsTcp) {
            adapter = getTcpAdapter((DsTcp) source);
        } else {
            EntityService<? extends EntityObject> service = ServiceManager.getService(source.getClass());
            if (service != null) {
                adapter = (DataSourceAdapter) service;
            }
        }

        return adapter;
    }
}
