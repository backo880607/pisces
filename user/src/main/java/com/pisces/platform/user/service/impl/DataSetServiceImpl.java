package com.pisces.platform.user.service.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.user.bean.DataSet;
import com.pisces.platform.user.dao.DataSetDao;
import com.pisces.platform.user.service.DataSetService;
import org.springframework.stereotype.Service;

@Service
class DataSetServiceImpl extends EntityServiceImpl<DataSet, DataSetDao> implements DataSetService {

	@Override
	public void register(DataSet dataSet) {
		
	}

	@Override
	public void unregister(DataSet dataSet) {
		
	}

}
