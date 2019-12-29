package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.DataSet;
import com.pisces.platform.user.controller.status.UserStatus;
import com.pisces.platform.user.service.DataSetService;
import com.pisces.platform.web.annotation.ExceptionMessage;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/DataSet")
public class DataSetController extends EntityController<DataSet, DataSetService> {
	@RequestMapping("register")
	@ExceptionMessage(clazz = UserStatus.class, name = "REGISTER")
	public ResponseData register(DataSet dataSet) {
		getService().register(dataSet);
		return succeed();
	}
	
	@RequestMapping("unregister")
	@ExceptionMessage(clazz = UserStatus.class, name = "UNREGISTER")
	public ResponseData unregister(DataSet dataSet) {
		getService().unregister(dataSet);
		return succeed();
	}
}
