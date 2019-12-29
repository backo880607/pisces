package com.pisces.platform.user.service;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.user.bean.DataSet;

public interface DataSetService extends EntityService<DataSet> {
	
	void register(DataSet dataSet);
	void unregister(DataSet dataSet);
}
