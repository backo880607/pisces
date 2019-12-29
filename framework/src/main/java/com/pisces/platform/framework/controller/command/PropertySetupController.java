package com.pisces.platform.framework.controller.command;

import com.pisces.platform.framework.bean.command.PropertySetup;
import com.pisces.platform.framework.service.command.PropertySetupService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/PropertySetup")
public class PropertySetupController extends EntityController<PropertySetup, PropertySetupService> {

}
