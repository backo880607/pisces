package com.pisces.platform.integration.controller;

import com.pisces.platform.integration.bean.SchemeGroup;
import com.pisces.platform.integration.service.SchemeGroupService;
import com.pisces.platform.web.controller.EntityController;
import com.pisces.platform.web.controller.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/SchemeGroup")
public class SchemeGroupController extends EntityController<SchemeGroup, SchemeGroupService> {

    @RequestMapping("/execute")
    public ResponseData execute(SchemeGroup schemeGroup) {
        SchemeGroup group = getService().getById(schemeGroup.getId());
        if (group != null) {
            getService().execute(group);
        }
        return succeed();
    }

}