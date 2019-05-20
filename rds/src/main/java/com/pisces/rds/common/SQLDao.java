package com.pisces.rds.common;

import com.pisces.core.dao.BaseDao;
import com.pisces.core.entity.EntityObject;
import com.pisces.rds.mapper.SQLMapper;

import tk.mybatis.mapper.common.Mapper;

public interface SQLDao<T extends EntityObject> extends BaseDao<T>, SQLMapper<T>, Mapper<T> {

}