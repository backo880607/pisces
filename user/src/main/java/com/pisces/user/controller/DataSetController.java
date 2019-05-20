package com.pisces.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.user.bean.DataSet;
import com.pisces.user.controller.status.UserStatus;
import com.pisces.user.service.DataSetService;
import com.pisces.web.annotation.ExceptionMessage;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

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
