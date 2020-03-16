package com.pisces.platform.user.controller;

import com.pisces.platform.user.bean.DataSet;
import com.pisces.platform.user.config.UserMessage;
import com.pisces.platform.user.service.DataSetService;
import com.pisces.platform.web.annotation.ExceptionMessage;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user/DataSet")
public class DataSetController extends EntityController<DataSet, DataSetService> {
	@RequestMapping("register")
	@ExceptionMessage(clazz = UserMessage.class, name = "REGISTER")
	public ResponseData register(DataSet dataSet) {
		getService().register(dataSet);
		return succeed();
	}
	
	@RequestMapping("unregister")
	@ExceptionMessage(clazz = UserMessage.class, name = "UNREGISTER")
	public ResponseData unregister(DataSet dataSet) {
		getService().unregister(dataSet);
		return succeed();
	}
}
