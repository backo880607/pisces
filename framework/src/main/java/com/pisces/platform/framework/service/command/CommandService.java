package com.pisces.platform.framework.service.command;

import com.pisces.platform.framework.bean.command.Command;

public interface CommandService {
	void execute(Command command);
}
