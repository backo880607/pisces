package com.pisces.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.core.entity.Property;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.EntityUtils;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

@RestController
@RequestMapping("/Property")
public class PropertyController extends EntityController<Property, PropertyService> {
	
	@RequestMapping("/getByClass")
	public ResponseData getByClass(String className) {
		return succeed(getService().getByClass(EntityUtils.getEntityClass(className)));
	}
}
