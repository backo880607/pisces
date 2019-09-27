package com.pisces.platform.controller.command;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.command.CommandGroup;
import com.pisces.platform.service.command.CommandGroupService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/platform/CommandGroup")
public class CommandGroupController extends EntityController<CommandGroup, CommandGroupService> {
	
	@GetMapping(value = "execute")
	public void execute(@RequestParam() Long groupId) {
		getService().execute(getService().selectById(groupId));
	}
}
