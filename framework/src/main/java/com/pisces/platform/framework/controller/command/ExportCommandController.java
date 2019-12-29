package com.pisces.platform.framework.controller.command;

import com.pisces.platform.framework.bean.command.ExportCommand;
import com.pisces.platform.framework.service.command.ExportCommandService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/ExportCommand")
public class ExportCommandController extends EntityController<ExportCommand, ExportCommandService> {

}
