package com.pisces.platform.rds.mapper;

import com.pisces.platform.rds.provider.CustomizeProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.Collection;
import java.util.List;

@RegisterMapper
public interface CustomizeMapper<T> {

    @SelectProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    void checkTable();

    @SelectProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    List<T> selectByIds(Collection<Long> idList);

    @InsertProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    int insertList(Collection<T> recordList);

    @UpdateProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    int update(T record);

    @UpdateProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    int updateList(Collection<T> recordList);

    @DeleteProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    int deleteList(Collection<T> recordList);

    @DeleteProvider(type = CustomizeProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKeys(Collection<Long> keyList);
}
