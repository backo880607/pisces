package com.pisces.platform.framework.service.command;

import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.framework.bean.command.CommandGroup;

public interface CommandGroupService extends EntityService<CommandGroup> {
	void execute(CommandGroup group);
}
