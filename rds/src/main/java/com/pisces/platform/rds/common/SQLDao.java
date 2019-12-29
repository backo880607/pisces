package com.pisces.platform.rds.common;

import com.pisces.platform.core.dao.BaseDao;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.rds.mapper.CustomizeMapper;
import tk.mybatis.mapper.common.Mapper;

public interface SQLDao<T extends EntityObject> extends BaseDao<T>, CustomizeMapper<T>, Mapper<T> {

}