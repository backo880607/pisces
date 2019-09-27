package com.pisces.platform.service.command.impl;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.platform.bean.command.Command;
import com.pisces.platform.bean.command.ExportCommand;
import com.pisces.platform.dao.command.ExportCommandDao;
import com.pisces.platform.service.command.ExportCommandService;

@Service
public class ExportCommandServiceImpl extends EntityServiceImpl<ExportCommand, ExportCommandDao> implements ExportCommandService {

	@Override
	public void execute(Command command) {
		if (command == null) {
			return;
		}
	}

}
