package com.pisces.platform.framework.service.command.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.framework.bean.command.Command;
import com.pisces.platform.framework.bean.command.ImportCommand;
import com.pisces.platform.framework.dao.command.ImportCommandDao;
import com.pisces.platform.framework.service.command.ImportCommandService;
import org.springframework.stereotype.Service;

@Service
public class ImportCommandServiceImpl extends EntityServiceImpl<ImportCommand, ImportCommandDao> implements ImportCommandService {

	@Override
	public void execute(Command command) {
		if (command == null) {
			return;
		}
	}

}
