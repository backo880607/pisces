package com.pisces.platform.integration.helper;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.integration.bean.DataSource;
import com.pisces.platform.integration.bean.FieldInfo;
import com.pisces.platform.integration.bean.Scheme;

import java.io.Closeable;
import java.util.Collection;

public interface DataSourceAdapter extends Closeable {
    void modifyConfig(DataConfig config);

    boolean validConnection(DataSource dataSource, String tableName, boolean export) throws Exception;

    boolean open(DataSource dataSource, String tableName, boolean export) throws Exception;

    void close();

    boolean executeQuery(DataSource dataSource, String tableName, Collection<FieldInfo> fields) throws Exception;

    Collection<FieldInfo> getFields() throws Exception;

    boolean step() throws Exception;

    String getData(int index) throws Exception;

    boolean beforeWriteTable(Scheme scheme, Collection<FieldInfo> fields) throws Exception;

    boolean beforeWriteEntity(EntityObject entity) throws Exception;

    void write(int index, String data) throws Exception;

    void afterWriteEntity(EntityObject entity) throws Exception;

    void afterWriteTable(Scheme scheme) throws Exception;
}
