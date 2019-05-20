package com.pisces.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.core.entity.Property;
import com.pisces.core.service.PropertyService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/Property")
public class PropertyController extends EntityController<Property, PropertyService> {
}
