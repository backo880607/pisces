package com.pisces.platform.framework.controller.system;

import com.pisces.platform.core.entity.Property;
import com.pisces.platform.core.service.PropertyService;
import com.pisces.platform.core.utils.EntityUtils;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Property")
public class PropertyController extends EntityController<Property, PropertyService> {

	@GetMapping("/getByClass")
	public ResponseData getByClass(String className) {
		return succeed(getService().get(EntityUtils.getEntityClass(className)));
	}
	
	@GetMapping("/getByCode")
	public ResponseData getByCode(String className, String code) {
		return succeed(getService().get(EntityUtils.getEntityClass(className), code));
	}
	
	@GetMapping("/getPrimaries")
	public ResponseData getPrimaries(String className) {
		return succeed(getService().getPrimaries(EntityUtils.getEntityClass(className)));
	}
}
