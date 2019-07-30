package com.pisces.rds.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.pisces.rds.provider.SQLProvider;

import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface SQLMapper<T> {

	@SelectProvider(type = SQLProvider.class, method = "dynamicSQL")
	void checkTable();
	
	@SelectProvider(type = SQLProvider.class, method = "dynamicSQL")
	List<T> selectMap(Collection<Long> ids);
	
	@InsertProvider(type = SQLProvider.class, method = "dynamicSQL")
	int insertList(Collection<T> recordList);
	
	@DeleteProvider(type = SQLProvider.class, method = "dynamicSQL")
	int deleteList(Collection<Long> recordList);
	
	@UpdateProvider(type = SQLProvider.class, method = "dynamicSQL")
    int update(T record);
}
