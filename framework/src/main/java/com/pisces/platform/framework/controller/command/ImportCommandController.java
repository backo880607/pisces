package com.pisces.platform.framework.controller.command;

import com.pisces.platform.framework.bean.command.ImportCommand;
import com.pisces.platform.framework.service.command.ImportCommandService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/ImportCommand")
public class ImportCommandController extends EntityController<ImportCommand, ImportCommandService> {

}
