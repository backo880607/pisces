package com.pisces.platform.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.command.PropertySetup;
import com.pisces.platform.service.command.PropertySetupService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/platform/PropertySetup")
public class PropertySetupController extends EntityController<PropertySetup, PropertySetupService> {

}
