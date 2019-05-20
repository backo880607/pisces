package com.pisces.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.core.entity.Property;
import com.pisces.core.service.PropertyService;
import com.pisces.core.utils.EntityUtils;
import com.pisces.platform.bean.Catalog;
import com.pisces.platform.service.CatalogService;
import com.pisces.web.controller.EntityController;
import com.pisces.web.controller.ResponseData;

@RestController
@RequestMapping("Catalog")
public class CatalogController extends EntityController<Catalog, CatalogService> {
	@Autowired
	private PropertyService propertyService;
	
	@GetMapping("/loadDefault")
	public ResponseData loadDefault(String tableName) {
		List<Property> properties = propertyService.getByClass(EntityUtils.getEntityClass(tableName));
		return succeed(properties);
	}
}
