package com.pisces.platform.controller.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pisces.platform.bean.system.BackupData;
import com.pisces.platform.service.system.BackupDataService;
import com.pisces.web.controller.EntityController;

@RestController
@RequestMapping("/platform/BackupData")
public class BackupDataController extends EntityController<BackupData, BackupDataService> {

}
