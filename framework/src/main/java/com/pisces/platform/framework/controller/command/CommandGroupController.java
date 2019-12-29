package com.pisces.platform.framework.controller.command;

import com.pisces.platform.framework.bean.command.CommandGroup;
import com.pisces.platform.framework.service.command.CommandGroupService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/CommandGroup")
public class CommandGroupController extends EntityController<CommandGroup, CommandGroupService> {
	
	@GetMapping(value = "execute")
	public void execute(@RequestParam() Long groupId) {
		getService().execute(getService().getById(groupId));
	}
}
