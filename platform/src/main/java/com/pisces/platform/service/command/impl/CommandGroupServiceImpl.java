package com.pisces.platform.service.command.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.pisces.core.service.EntityServiceImpl;
import com.pisces.core.service.ServiceManager;
import com.pisces.platform.bean.command.Command;
import com.pisces.platform.bean.command.CommandGroup;
import com.pisces.platform.dao.command.CommandGroupDao;
import com.pisces.platform.service.command.CommandGroupService;
import com.pisces.platform.service.command.CommandService;

@Service
public class CommandGroupServiceImpl extends EntityServiceImpl<CommandGroup, CommandGroupDao> implements CommandGroupService {

	@Override
	public void execute(CommandGroup group) {
		if (group == null) {
			return;
		}
		
		Collection<Command> commands = group.getCommands();
		for (Command command : commands) {
			CommandService service = (CommandService)ServiceManager.getService(command.getClass());
			service.execute(command);
		}
	}

}
