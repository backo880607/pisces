package com.pisces.platform.framework.controller.system;

import com.pisces.platform.framework.bean.system.BackupData;
import com.pisces.platform.framework.service.system.BackupDataService;
import com.pisces.platform.web.controller.EntityController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/platform/BackupData")
public class BackupDataController extends EntityController<BackupData, BackupDataService> {

}
