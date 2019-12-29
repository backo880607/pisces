package com.pisces.platform.framework.service.command.impl;

import com.pisces.platform.core.service.EntityServiceImpl;
import com.pisces.platform.core.service.ServiceManager;
import com.pisces.platform.framework.bean.command.Command;
import com.pisces.platform.framework.bean.command.CommandGroup;
import com.pisces.platform.framework.dao.command.CommandGroupDao;
import com.pisces.platform.framework.service.command.CommandGroupService;
import com.pisces.platform.framework.service.command.CommandService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommandGroupServiceImpl extends EntityServiceImpl<CommandGroup, CommandGroupDao> implements CommandGroupService {

	@Override
	public void execute(CommandGroup group) {
		if (group == null) {
			return;
		}
		
		Collection<Command> commands = group.getCommands();
		for (Command command : commands) {
			CommandService service = (CommandService) ServiceManager.getService(command.getClass());
			service.execute(command);
		}
	}

}
