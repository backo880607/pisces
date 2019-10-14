package com.pisces.integration.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.integration.bean.SchemeGroup;
import com.pisces.integration.service.SchemeGroupService;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

@RestController
@RequestMapping("/integration/SchemeGroup")
public class SchemeGroupController extends EntityController<SchemeGroup, SchemeGroupService> {
	
	@RequestMapping("/execute")
	public ResponseData execute(SchemeGroup schemeGroup) {
		SchemeGroup group = getService().getById(schemeGroup.getId());
		if (group != null) {
			getService().execute(group);
		}
		return succeed();
	}

}