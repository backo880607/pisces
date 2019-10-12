package com.pisces.user.service.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.user.bean.DataSet;
import com.pisces.user.dao.DataSetDao;
import com.pisces.user.service.DataSetService;

@Service
class DataSetServiceImpl extends EntityServiceImpl<DataSet, DataSetDao> implements DataSetService {

	@Override
	public void register(DataSet dataSet) {
		
	}

	@Override
	public void unregister(DataSet dataSet) {
		
	}

}
