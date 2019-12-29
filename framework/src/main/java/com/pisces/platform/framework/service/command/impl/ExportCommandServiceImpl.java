package com.pisces.platform.framework.service.command.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.framework.bean.command.Command;
import com.pisces.platform.framework.bean.command.ExportCommand;
import com.pisces.platform.framework.dao.command.ExportCommandDao;
import com.pisces.platform.framework.service.command.ExportCommandService;
import org.springframework.stereotype.Service;

@Service
public class ExportCommandServiceImpl extends EntityServiceImpl<ExportCommand, ExportCommandDao> implements ExportCommandService {

	@Override
	public void execute(Command command) {
		if (command == null) {
			return;
		}
	}

}
