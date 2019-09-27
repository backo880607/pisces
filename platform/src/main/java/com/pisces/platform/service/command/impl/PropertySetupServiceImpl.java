package com.pisces.platform.service.command.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.command.Command;
import com.pisces.platform.bean.command.PropertySetup;
import com.pisces.platform.dao.command.PropertySetupDao;
import com.pisces.platform.service.command.PropertySetupService;

@Service
public class PropertySetupServiceImpl extends EntityServiceImpl<PropertySetup, PropertySetupDao> implements PropertySetupService {

	@Override
	public void execute(Command command) {
	}

}
