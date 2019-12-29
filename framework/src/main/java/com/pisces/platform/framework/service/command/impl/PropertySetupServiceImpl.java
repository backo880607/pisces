package com.pisces.platform.framework.service.command.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.framework.bean.command.Command;
import com.pisces.platform.framework.bean.command.PropertySetup;
import com.pisces.platform.framework.dao.command.PropertySetupDao;
import com.pisces.platform.framework.service.command.PropertySetupService;
import org.springframework.stereotype.Service;

@Service
public class PropertySetupServiceImpl extends EntityServiceImpl<PropertySetup, PropertySetupDao> implements PropertySetupService {

	@Override
	public void execute(Command command) {
	}

}
