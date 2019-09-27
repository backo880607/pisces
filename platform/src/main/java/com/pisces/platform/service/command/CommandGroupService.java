package com.pisces.platform.service.command;

import com.pisces.core.service.EntityService;
import com.pisces.platform.bean.command.CommandGroup;

public interface CommandGroupService extends EntityService<CommandGroup> {
	void execute(CommandGroup group);
}
