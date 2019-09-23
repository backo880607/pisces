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
	List<T> selectByIds(Collection<Long> idList);
	
	@InsertProvider(type = SQLProvider.class, method = "dynamicSQL")
	int insertList(Collection<T> recordList);
	
	@UpdateProvider(type = SQLProvider.class, method = "dynamicSQL")
    int update(T record);
	
	@UpdateProvider(type = SQLProvider.class, method = "dynamicSQL")
	int updateList(Collection<T> recordList);
	
	@DeleteProvider(type = SQLProvider.class, method = "dynamicSQL")
	int deleteList(Collection<T> recordList);
	
	@DeleteProvider(type = SQLProvider.class, method = "dynamicSQL")
	int deleteByPrimaryKeys(Collection<Long> keyList);
}
