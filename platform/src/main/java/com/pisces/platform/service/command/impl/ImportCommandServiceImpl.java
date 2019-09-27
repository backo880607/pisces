package com.pisces.platform.service.command.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.command.Command;
import com.pisces.platform.bean.command.ImportCommand;
import com.pisces.platform.dao.command.ImportCommandDao;
import com.pisces.platform.service.command.ImportCommandService;

@Service
public class ImportCommandServiceImpl extends EntityServiceImpl<ImportCommand, ImportCommandDao> implements ImportCommandService {

	@Override
	public void execute(Command command) {
		if (command == null) {
			return;
		}
	}

}
