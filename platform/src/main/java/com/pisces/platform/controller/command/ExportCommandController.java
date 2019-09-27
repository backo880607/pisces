package com.pisces.platform.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.command.ExportCommand;
import com.pisces.platform.service.command.ExportCommandService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/platform/ExportCommand")
public class ExportCommandController extends EntityController<ExportCommand, ExportCommandService> {

}
