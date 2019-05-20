package com.pisces.user.service;

import com.pisces.core.service.EntityService;
import com.pisces.user.bean.DataSet;

public interface DataSetService extends EntityService<DataSet> {
	
	void register(DataSet dataSet);
	void unregister(DataSet dataSet);
}
