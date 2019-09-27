package com.pisces.platform.controller.command;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.command.ImportCommand;
import com.pisces.platform.service.command.ImportCommandService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/platform/ImportCommand")
public class ImportCommandController extends EntityController<ImportCommand, ImportCommandService> {

}
